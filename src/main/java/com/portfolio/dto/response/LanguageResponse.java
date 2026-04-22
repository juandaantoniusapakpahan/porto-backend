package com.portfolio.dto.response;

import com.portfolio.entity.enums.LanguageProficiency;

import java.util.UUID;

public record LanguageResponse(
        UUID id,
        String languageName,
        LanguageProficiency proficiency,
        int orderIndex
) {}
