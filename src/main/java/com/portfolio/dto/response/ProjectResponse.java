package com.portfolio.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProjectResponse(
        UUID id,
        String name,
        String description,
        List<String> techStack,
        String projectUrl,
        String repoUrl,
        String thumbnailUrl,
        LocalDate startDate,
        LocalDate endDate,
        int orderIndex
) {}
