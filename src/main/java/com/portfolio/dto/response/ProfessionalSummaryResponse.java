package com.portfolio.dto.response;

import java.util.UUID;

public record ProfessionalSummaryResponse(
        UUID id,
        String content
) {}
