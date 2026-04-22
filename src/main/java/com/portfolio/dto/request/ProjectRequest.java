package com.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;

public record ProjectRequest(
        @NotBlank(message = "Project name is required")
        @Size(max = 255)
        String name,

        @Size(max = 5000)
        String description,

        List<@Size(max = 100) String> techStack,

        @URL(message = "Invalid project URL")
        String projectUrl,

        @URL(message = "Invalid repository URL")
        String repoUrl,

        @URL(message = "Invalid thumbnail URL")
        String thumbnailUrl,

        LocalDate startDate,

        LocalDate endDate,

        int orderIndex
) {}
