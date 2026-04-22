package com.portfolio.controller;

import com.portfolio.dto.ApiResponse;
import com.portfolio.dto.request.*;
import com.portfolio.dto.response.*;
import com.portfolio.security.UserPrincipal;
import com.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
@Tag(name = "Portfolio", description = "Manage all portfolio sections")
@SecurityRequirement(name = "bearerAuth")
public class PortfolioController {

    private final PortfolioService portfolioService;

    // ===== Personal Info =====

    @GetMapping("/personal-info")
    @Operation(summary = "Get personal info")
    public ResponseEntity<ApiResponse<PersonalInfoResponse>> getPersonalInfo(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success(portfolioService.getPersonalInfo(principal.id())));
    }

    @PutMapping("/personal-info")
    @Operation(summary = "Create or update personal info")
    public ResponseEntity<ApiResponse<PersonalInfoResponse>> upsertPersonalInfo(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody PersonalInfoRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                portfolioService.upsertPersonalInfo(principal.id(), request),
                "Personal info saved"
        ));
    }

    @PostMapping(value = "/personal-info/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload or replace profile photo")
    public ResponseEntity<ApiResponse<PersonalInfoResponse>> uploadAvatar(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(ApiResponse.success(
                portfolioService.uploadAvatar(principal.id(), file),
                "Profile photo uploaded"
        ));
    }

    @DeleteMapping("/personal-info/avatar")
    @Operation(summary = "Delete profile photo")
    public ResponseEntity<ApiResponse<PersonalInfoResponse>> deleteAvatar(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success(
                portfolioService.deleteAvatar(principal.id()),
                "Profile photo deleted"
        ));
    }

    // ===== Professional Summary =====

    @GetMapping("/summary")
    @Operation(summary = "Get professional summary")
    public ResponseEntity<ApiResponse<ProfessionalSummaryResponse>> getSummary(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success(portfolioService.getSummary(principal.id())));
    }

    @PutMapping("/summary")
    @Operation(summary = "Create or update professional summary")
    public ResponseEntity<ApiResponse<ProfessionalSummaryResponse>> upsertSummary(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody ProfessionalSummaryRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                portfolioService.upsertSummary(principal.id(), request),
                "Summary saved"
        ));
    }

    // ===== Work Experience =====

    @GetMapping("/experiences")
    @Operation(summary = "Get all work experiences")
    public ResponseEntity<ApiResponse<List<WorkExperienceResponse>>> getExperiences(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success(portfolioService.getExperiences(principal.id())));
    }

    @PostMapping("/experiences")
    @Operation(summary = "Add a work experience")
    public ResponseEntity<ApiResponse<WorkExperienceResponse>> createExperience(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody WorkExperienceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(portfolioService.createExperience(principal.id(), request), "Work experience added"));
    }

    @PutMapping("/experiences/{id}")
    @Operation(summary = "Update a work experience")
    public ResponseEntity<ApiResponse<WorkExperienceResponse>> updateExperience(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id,
            @Valid @RequestBody WorkExperienceRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                portfolioService.updateExperience(principal.id(), id, request),
                "Work experience updated"
        ));
    }

    @DeleteMapping("/experiences/{id}")
    @Operation(summary = "Delete a work experience")
    public ResponseEntity<ApiResponse<Void>> deleteExperience(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id) {
        portfolioService.deleteExperience(principal.id(), id);
        return ResponseEntity.ok(ApiResponse.success("Work experience deleted"));
    }

    // ===== Education =====

    @GetMapping("/educations")
    @Operation(summary = "Get all educations")
    public ResponseEntity<ApiResponse<List<EducationResponse>>> getEducations(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success(portfolioService.getEducations(principal.id())));
    }

    @PostMapping("/educations")
    @Operation(summary = "Add an education entry")
    public ResponseEntity<ApiResponse<EducationResponse>> createEducation(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody EducationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(portfolioService.createEducation(principal.id(), request), "Education added"));
    }

    @PutMapping("/educations/{id}")
    @Operation(summary = "Update an education entry")
    public ResponseEntity<ApiResponse<EducationResponse>> updateEducation(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id,
            @Valid @RequestBody EducationRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                portfolioService.updateEducation(principal.id(), id, request),
                "Education updated"
        ));
    }

    @DeleteMapping("/educations/{id}")
    @Operation(summary = "Delete an education entry")
    public ResponseEntity<ApiResponse<Void>> deleteEducation(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id) {
        portfolioService.deleteEducation(principal.id(), id);
        return ResponseEntity.ok(ApiResponse.success("Education deleted"));
    }

    // ===== Skills =====

    @GetMapping("/skills")
    @Operation(summary = "Get all skills")
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getSkills(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success(portfolioService.getSkills(principal.id())));
    }

    @PostMapping("/skills")
    @Operation(summary = "Add a skill")
    public ResponseEntity<ApiResponse<SkillResponse>> createSkill(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody SkillRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(portfolioService.createSkill(principal.id(), request), "Skill added"));
    }

    @PutMapping("/skills/{id}")
    @Operation(summary = "Update a skill")
    public ResponseEntity<ApiResponse<SkillResponse>> updateSkill(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id,
            @Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                portfolioService.updateSkill(principal.id(), id, request),
                "Skill updated"
        ));
    }

    @DeleteMapping("/skills/{id}")
    @Operation(summary = "Delete a skill")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id) {
        portfolioService.deleteSkill(principal.id(), id);
        return ResponseEntity.ok(ApiResponse.success("Skill deleted"));
    }

    // ===== Certifications =====

    @GetMapping("/certifications")
    @Operation(summary = "Get all certifications")
    public ResponseEntity<ApiResponse<List<CertificationResponse>>> getCertifications(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success(portfolioService.getCertifications(principal.id())));
    }

    @PostMapping("/certifications")
    @Operation(summary = "Add a certification")
    public ResponseEntity<ApiResponse<CertificationResponse>> createCertification(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody CertificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(portfolioService.createCertification(principal.id(), request), "Certification added"));
    }

    @PutMapping("/certifications/{id}")
    @Operation(summary = "Update a certification")
    public ResponseEntity<ApiResponse<CertificationResponse>> updateCertification(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id,
            @Valid @RequestBody CertificationRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                portfolioService.updateCertification(principal.id(), id, request),
                "Certification updated"
        ));
    }

    @DeleteMapping("/certifications/{id}")
    @Operation(summary = "Delete a certification")
    public ResponseEntity<ApiResponse<Void>> deleteCertification(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id) {
        portfolioService.deleteCertification(principal.id(), id);
        return ResponseEntity.ok(ApiResponse.success("Certification deleted"));
    }

    // ===== Projects =====

    @GetMapping("/projects")
    @Operation(summary = "Get all projects")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getProjects(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success(portfolioService.getProjects(principal.id())));
    }

    @PostMapping("/projects")
    @Operation(summary = "Add a project")
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(portfolioService.createProject(principal.id(), request), "Project added"));
    }

    @PutMapping("/projects/{id}")
    @Operation(summary = "Update a project")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id,
            @Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                portfolioService.updateProject(principal.id(), id, request),
                "Project updated"
        ));
    }

    @DeleteMapping("/projects/{id}")
    @Operation(summary = "Delete a project")
    public ResponseEntity<ApiResponse<Void>> deleteProject(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id) {
        portfolioService.deleteProject(principal.id(), id);
        return ResponseEntity.ok(ApiResponse.success("Project deleted"));
    }

    // ===== Awards =====

    @GetMapping("/awards")
    @Operation(summary = "Get all awards")
    public ResponseEntity<ApiResponse<List<AwardResponse>>> getAwards(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success(portfolioService.getAwards(principal.id())));
    }

    @PostMapping("/awards")
    @Operation(summary = "Add an award")
    public ResponseEntity<ApiResponse<AwardResponse>> createAward(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody AwardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(portfolioService.createAward(principal.id(), request), "Award added"));
    }

    @PutMapping("/awards/{id}")
    @Operation(summary = "Update an award")
    public ResponseEntity<ApiResponse<AwardResponse>> updateAward(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id,
            @Valid @RequestBody AwardRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                portfolioService.updateAward(principal.id(), id, request),
                "Award updated"
        ));
    }

    @DeleteMapping("/awards/{id}")
    @Operation(summary = "Delete an award")
    public ResponseEntity<ApiResponse<Void>> deleteAward(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id) {
        portfolioService.deleteAward(principal.id(), id);
        return ResponseEntity.ok(ApiResponse.success("Award deleted"));
    }

    // ===== Languages =====

    @GetMapping("/languages")
    @Operation(summary = "Get all languages")
    public ResponseEntity<ApiResponse<List<LanguageResponse>>> getLanguages(
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success(portfolioService.getLanguages(principal.id())));
    }

    @PostMapping("/languages")
    @Operation(summary = "Add a language")
    public ResponseEntity<ApiResponse<LanguageResponse>> createLanguage(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody LanguageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(portfolioService.createLanguage(principal.id(), request), "Language added"));
    }

    @PutMapping("/languages/{id}")
    @Operation(summary = "Update a language")
    public ResponseEntity<ApiResponse<LanguageResponse>> updateLanguage(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id,
            @Valid @RequestBody LanguageRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                portfolioService.updateLanguage(principal.id(), id, request),
                "Language updated"
        ));
    }

    @DeleteMapping("/languages/{id}")
    @Operation(summary = "Delete a language")
    public ResponseEntity<ApiResponse<Void>> deleteLanguage(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id) {
        portfolioService.deleteLanguage(principal.id(), id);
        return ResponseEntity.ok(ApiResponse.success("Language deleted"));
    }
}
