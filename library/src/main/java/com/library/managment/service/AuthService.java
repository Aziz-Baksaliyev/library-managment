package com.library.managment.service;

import com.library.managment.model.dto.auth.AuthResponse;
import com.library.managment.model.dto.auth.RegisterRequest;
import com.library.managment.model.entity.User;
import com.library.managment.model.enums.Role;
import com.library.managment.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}