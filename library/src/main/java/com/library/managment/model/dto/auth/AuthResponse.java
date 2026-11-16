package com.library.managment.model.dto.auth;

public record AuthResponse(
        String message,
        String username,
        String role
) {}
