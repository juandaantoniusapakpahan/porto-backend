package com.portfolio.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record AwardResponse(
        UUID id,
        String title,
        String issuer,
        LocalDate date,
        String description,
        int orderIndex
) {}
