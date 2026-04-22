package com.portfolio.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record EducationRequest(
        @NotBlank(message = "Institution name is required")
        @Size(max = 255)
        String institution,

        @Size(max = 255)
        String degree,

        @Size(max = 255)
        String major,

        @Min(value = 1900, message = "Start year must be after 1900")
        @Max(value = 2100, message = "Start year is invalid")
        Integer startYear,

        @Min(value = 1900, message = "End year must be after 1900")
        @Max(value = 2100, message = "End year is invalid")
        Integer endYear,

        @DecimalMin(value = "0.0", message = "GPA cannot be negative")
        @DecimalMax(value = "4.0", message = "GPA cannot exceed 4.0")
        @Digits(integer = 1, fraction = 2)
        BigDecimal gpa,

        int orderIndex
) {}
