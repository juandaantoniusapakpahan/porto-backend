package com.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email or username is required")
        String usernameOrEmail,

        @NotBlank(message = "Password is required")
        String password
) {}
