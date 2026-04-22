package com.portfolio.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record WorkExperienceResponse(
        UUID id,
        String companyName,
        String location,
        String position,
        LocalDate startDate,
        LocalDate endDate,
        boolean isCurrent,
        List<String> descriptionPoints,
        int orderIndex
) {}
