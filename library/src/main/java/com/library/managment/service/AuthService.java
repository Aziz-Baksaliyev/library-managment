package com.library.managment.service;

import com.library.managment.model.dto.auth.AuthResponse;
import com.library.managment.model.dto.auth.LoginRequest;
import com.library.managment.model.dto.auth.RegisterRequest;
import com.library.managment.model.entity.User;
import com.library.managment.model.enums.Role;
import com.library.managment.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username Already Exist.");
        }

        if (userRepository.existsByUsername(request.getEmail())) {
            throw new RuntimeException("Email Already Exist.");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        try {
            userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username or email already exists",
                    ex
            );
        }

        return new AuthResponse(
                "Registration is done!",
                user.getUsername(),
                user.getRole().name()
        );
    }

    public AuthResponse login(
            LoginRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadRequestException ex) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password."
            );
        }

        HttpServletRequest.getSession(true);
        HttpServletRequest.changeSessionId();

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(
                context,
                HttpServletRequest,
                HttpServletResponse
        );

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replaceFirst("^ROLE_", "");

        return new AuthResponse(
                "Login successful",
                authentication.getName(),
                role
        );
    }
}