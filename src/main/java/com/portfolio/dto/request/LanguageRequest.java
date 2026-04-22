package com.portfolio.dto.request;

import com.portfolio.entity.enums.LanguageProficiency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LanguageRequest(
        @NotBlank(message = "Language name is required")
        @Size(max = 100)
        String languageName,

        @NotNull(message = "Proficiency is required")
        LanguageProficiency proficiency,

        int orderIndex
) {}
