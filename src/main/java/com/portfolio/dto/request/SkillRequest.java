package com.portfolio.dto.request;

import com.portfolio.entity.enums.ProficiencyLevel;
import com.portfolio.entity.enums.SkillCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SkillRequest(
        @NotBlank(message = "Skill name is required")
        @Size(max = 100)
        String name,

        @NotNull(message = "Category is required")
        SkillCategory category,

        @NotNull(message = "Proficiency level is required")
        ProficiencyLevel proficiencyLevel,

        int orderIndex
) {}
