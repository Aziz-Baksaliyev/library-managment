package com.library.managment.service;

import com.library.managment.model.dto.auth.RegisterRequest;
import com.library.managment.model.entity.User;
import com.library.managment.model.enums.Role;
import com.library.managment.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void register(PasswordEncoder passwordEncoder, RegisterRequest request) {
        String hashPass = passwordEncoder.encode(request.getPassword());

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(hashPass);
        user.setRole(Role.USER);

        userRepository.save(user);
    }
}