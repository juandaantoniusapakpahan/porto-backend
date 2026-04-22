package com.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AwardRequest(
        @NotBlank(message = "Award title is required")
        @Size(max = 255)
        String title,

        @Size(max = 255)
        String issuer,

        LocalDate date,

        @Size(max = 2000)
        String description,

        int orderIndex
) {}
