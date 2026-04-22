package com.portfolio.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        String avatarUrl,
        LocalDateTime createdAt
) {}
