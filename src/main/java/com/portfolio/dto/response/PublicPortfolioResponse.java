package com.portfolio.dto.response;

import java.util.List;

public record PublicPortfolioResponse(
        UserResponse user,
        PersonalInfoResponse personalInfo,
        ProfessionalSummaryResponse summary,
        List<WorkExperienceResponse> experiences,
        List<EducationResponse> educations,
        List<SkillResponse> skills,
        List<CertificationResponse> certifications,
        List<ProjectResponse> projects,
        List<AwardResponse> awards,
        List<LanguageResponse> languages
) {}
