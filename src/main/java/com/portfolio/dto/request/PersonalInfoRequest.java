package com.portfolio.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.Map;

public record PersonalInfoRequest(
        @Size(max = 255)
        String fullName,

        @Size(max = 255)
        String professionalTitle,

        @Size(max = 50)
        String phone,

        @Email(message = "Invalid email format")
        @Size(max = 255)
        String email,

        @Size(max = 255)
        String domicile,

        @URL(message = "Invalid LinkedIn URL")
        String linkedinUrl,

        @URL(message = "Invalid GitHub URL")
        String githubUrl,

        @URL(message = "Invalid website URL")
        String websiteUrl,

        Map<String, String> otherLinks
) {}
