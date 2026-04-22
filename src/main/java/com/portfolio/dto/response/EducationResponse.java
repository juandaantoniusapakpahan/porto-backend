package com.portfolio.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record EducationResponse(
        UUID id,
        String institution,
        String degree,
        String major,
        Integer startYear,
        Integer endYear,
        BigDecimal gpa,
        int orderIndex
) {}
