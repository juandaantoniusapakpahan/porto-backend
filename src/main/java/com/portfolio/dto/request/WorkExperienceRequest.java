package com.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record WorkExperienceRequest(
        @NotBlank(message = "Company name is required")
        @Size(max = 255)
        String companyName,

        @Size(max = 255)
        String location,

        @NotBlank(message = "Position is required")
        @Size(max = 255)
        String position,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        LocalDate endDate,

        boolean isCurrent,

        List<@Size(max = 1000) String> descriptionPoints,

        int orderIndex
) {}
