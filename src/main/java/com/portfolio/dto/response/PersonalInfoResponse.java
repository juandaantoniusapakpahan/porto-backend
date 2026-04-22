package com.portfolio.dto.response;

import java.util.Map;
import java.util.UUID;

public record PersonalInfoResponse(
        UUID id,
        String fullName,
        String professionalTitle,
        String phone,
        String email,
        String domicile,
        String avatarUrl,
        String linkedinUrl,
        String githubUrl,
        String websiteUrl,
        Map<String, String> otherLinks
) {}
