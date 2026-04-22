package com.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

public record CertificationRequest(
        @NotBlank(message = "Certification name is required")
        @Size(max = 255)
        String name,

        @NotBlank(message = "Issuer is required")
        @Size(max = 255)
        String issuer,

        LocalDate issueDate,

        LocalDate expiryDate,

        @URL(message = "Invalid credential URL")
        String credentialUrl,

        int orderIndex
) {}
