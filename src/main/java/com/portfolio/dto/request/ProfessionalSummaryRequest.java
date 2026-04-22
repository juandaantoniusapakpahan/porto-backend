package com.portfolio.dto.request;

import jakarta.validation.constraints.Size;

public record ProfessionalSummaryRequest(
        @Size(max = 5000, message = "Summary must be at most 5000 characters")
        String content
) {}
