package com.portfolio.dto.response;

import com.portfolio.entity.enums.ProficiencyLevel;
import com.portfolio.entity.enums.SkillCategory;

import java.util.UUID;

public record SkillResponse(
        UUID id,
        String name,
        SkillCategory category,
        ProficiencyLevel proficiencyLevel,
        int orderIndex
) {}
