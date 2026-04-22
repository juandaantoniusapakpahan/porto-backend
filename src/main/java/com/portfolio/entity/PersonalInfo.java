package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "personal_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "full_name", length = 255)
    private String fullName;

    @Column(name = "professional_title", length = 255)
    private String professionalTitle;

    @Column(length = 50)
    private String phone;

    @Column(length = 255)
    private String email;

    @Column(length = 255)
    private String domicile;

    @Column(name = "linkedin_url", columnDefinition = "TEXT")
    private String linkedinUrl;

    @Column(name = "github_url", columnDefinition = "TEXT")
    private String githubUrl;

    @Column(name = "website_url", columnDefinition = "TEXT")
    private String websiteUrl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "other_links", columnDefinition = "jsonb")
    @Builder.Default
    private Map<String, String> otherLinks = new HashMap<>();

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
