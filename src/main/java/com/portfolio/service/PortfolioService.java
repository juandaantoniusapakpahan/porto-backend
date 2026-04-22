package com.portfolio.service;

import com.portfolio.dto.request.*;
import com.portfolio.dto.response.*;
import com.portfolio.entity.*;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.*;
import com.portfolio.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final UserRepository userRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final ProfessionalSummaryRepository summaryRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final EducationRepository educationRepository;
    private final SkillRepository skillRepository;
    private final CertificationRepository certificationRepository;
    private final ProjectRepository projectRepository;
    private final AwardRepository awardRepository;
    private final LanguageRepository languageRepository;

    private final PersonalInfoMapper personalInfoMapper;
    private final ProfessionalSummaryMapper summaryMapper;
    private final WorkExperienceMapper workExperienceMapper;
    private final EducationMapper educationMapper;
    private final SkillMapper skillMapper;
    private final CertificationMapper certificationMapper;
    private final ProjectMapper projectMapper;
    private final AwardMapper awardMapper;
    private final LanguageMapper languageMapper;

    // ===== Personal Info =====

    @Transactional(readOnly = true)
    public PersonalInfoResponse getPersonalInfo(UUID userId) {
        return personalInfoRepository.findByUserId(userId)
                .map(personalInfoMapper::toResponse)
                .orElse(null);
    }

    @Transactional
    public PersonalInfoResponse upsertPersonalInfo(UUID userId, PersonalInfoRequest request) {
        User user = getUser(userId);
        PersonalInfo info = personalInfoRepository.findByUser(user)
                .orElse(PersonalInfo.builder().user(user).build());

        personalInfoMapper.updateFromRequest(request, info);
        return personalInfoMapper.toResponse(personalInfoRepository.save(info));
    }

    // ===== Professional Summary =====

    @Transactional(readOnly = true)
    public ProfessionalSummaryResponse getSummary(UUID userId) {
        return summaryRepository.findByUserId(userId)
                .map(summaryMapper::toResponse)
                .orElse(null);
    }

    @Transactional
    public ProfessionalSummaryResponse upsertSummary(UUID userId, ProfessionalSummaryRequest request) {
        User user = getUser(userId);
        ProfessionalSummary summary = summaryRepository.findByUser(user)
                .orElse(ProfessionalSummary.builder().user(user).build());

        summaryMapper.updateFromRequest(request, summary);
        return summaryMapper.toResponse(summaryRepository.save(summary));
    }

    // ===== Work Experience =====

    @Transactional(readOnly = true)
    public List<WorkExperienceResponse> getExperiences(UUID userId) {
        return workExperienceMapper.toResponseList(workExperienceRepository.findByUserIdOrderByOrderIndexAsc(userId));
    }

    @Transactional
    public WorkExperienceResponse createExperience(UUID userId, WorkExperienceRequest request) {
        User user = getUser(userId);
        WorkExperience entity = workExperienceMapper.toEntity(request);
        entity.setUser(user);
        return workExperienceMapper.toResponse(workExperienceRepository.save(entity));
    }

    @Transactional
    public WorkExperienceResponse updateExperience(UUID userId, UUID id, WorkExperienceRequest request) {
        WorkExperience entity = workExperienceRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkExperience", id));
        workExperienceMapper.updateFromRequest(request, entity);
        return workExperienceMapper.toResponse(workExperienceRepository.save(entity));
    }

    @Transactional
    public void deleteExperience(UUID userId, UUID id) {
        WorkExperience entity = workExperienceRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkExperience", id));
        workExperienceRepository.delete(entity);
    }

    // ===== Education =====

    @Transactional(readOnly = true)
    public List<EducationResponse> getEducations(UUID userId) {
        return educationMapper.toResponseList(educationRepository.findByUserIdOrderByOrderIndexAsc(userId));
    }

    @Transactional
    public EducationResponse createEducation(UUID userId, EducationRequest request) {
        User user = getUser(userId);
        Education entity = educationMapper.toEntity(request);
        entity.setUser(user);
        return educationMapper.toResponse(educationRepository.save(entity));
    }

    @Transactional
    public EducationResponse updateEducation(UUID userId, UUID id, EducationRequest request) {
        Education entity = educationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Education", id));
        educationMapper.updateFromRequest(request, entity);
        return educationMapper.toResponse(educationRepository.save(entity));
    }

    @Transactional
    public void deleteEducation(UUID userId, UUID id) {
        Education entity = educationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Education", id));
        educationRepository.delete(entity);
    }

    // ===== Skills =====

    @Transactional(readOnly = true)
    public List<SkillResponse> getSkills(UUID userId) {
        return skillMapper.toResponseList(skillRepository.findByUserIdOrderByOrderIndexAsc(userId));
    }

    @Transactional
    public SkillResponse createSkill(UUID userId, SkillRequest request) {
        User user = getUser(userId);
        Skill entity = skillMapper.toEntity(request);
        entity.setUser(user);
        return skillMapper.toResponse(skillRepository.save(entity));
    }

    @Transactional
    public SkillResponse updateSkill(UUID userId, UUID id, SkillRequest request) {
        Skill entity = skillRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", id));
        skillMapper.updateFromRequest(request, entity);
        return skillMapper.toResponse(skillRepository.save(entity));
    }

    @Transactional
    public void deleteSkill(UUID userId, UUID id) {
        Skill entity = skillRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", id));
        skillRepository.delete(entity);
    }

    // ===== Certifications =====

    @Transactional(readOnly = true)
    public List<CertificationResponse> getCertifications(UUID userId) {
        return certificationMapper.toResponseList(certificationRepository.findByUserIdOrderByOrderIndexAsc(userId));
    }

    @Transactional
    public CertificationResponse createCertification(UUID userId, CertificationRequest request) {
        User user = getUser(userId);
        Certification entity = certificationMapper.toEntity(request);
        entity.setUser(user);
        return certificationMapper.toResponse(certificationRepository.save(entity));
    }

    @Transactional
    public CertificationResponse updateCertification(UUID userId, UUID id, CertificationRequest request) {
        Certification entity = certificationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Certification", id));
        certificationMapper.updateFromRequest(request, entity);
        return certificationMapper.toResponse(certificationRepository.save(entity));
    }

    @Transactional
    public void deleteCertification(UUID userId, UUID id) {
        Certification entity = certificationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Certification", id));
        certificationRepository.delete(entity);
    }

    // ===== Projects =====

    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjects(UUID userId) {
        return projectMapper.toResponseList(projectRepository.findByUserIdOrderByOrderIndexAsc(userId));
    }

    @Transactional
    public ProjectResponse createProject(UUID userId, ProjectRequest request) {
        User user = getUser(userId);
        Project entity = projectMapper.toEntity(request);
        entity.setUser(user);
        return projectMapper.toResponse(projectRepository.save(entity));
    }

    @Transactional
    public ProjectResponse updateProject(UUID userId, UUID id, ProjectRequest request) {
        Project entity = projectRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
        projectMapper.updateFromRequest(request, entity);
        return projectMapper.toResponse(projectRepository.save(entity));
    }

    @Transactional
    public void deleteProject(UUID userId, UUID id) {
        Project entity = projectRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
        projectRepository.delete(entity);
    }

    // ===== Awards =====

    @Transactional(readOnly = true)
    public List<AwardResponse> getAwards(UUID userId) {
        return awardMapper.toResponseList(awardRepository.findByUserIdOrderByOrderIndexAsc(userId));
    }

    @Transactional
    public AwardResponse createAward(UUID userId, AwardRequest request) {
        User user = getUser(userId);
        Award entity = awardMapper.toEntity(request);
        entity.setUser(user);
        return awardMapper.toResponse(awardRepository.save(entity));
    }

    @Transactional
    public AwardResponse updateAward(UUID userId, UUID id, AwardRequest request) {
        Award entity = awardRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Award", id));
        awardMapper.updateFromRequest(request, entity);
        return awardMapper.toResponse(awardRepository.save(entity));
    }

    @Transactional
    public void deleteAward(UUID userId, UUID id) {
        Award entity = awardRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Award", id));
        awardRepository.delete(entity);
    }

    // ===== Languages =====

    @Transactional(readOnly = true)
    public List<LanguageResponse> getLanguages(UUID userId) {
        return languageMapper.toResponseList(languageRepository.findByUserIdOrderByOrderIndexAsc(userId));
    }

    @Transactional
    public LanguageResponse createLanguage(UUID userId, LanguageRequest request) {
        User user = getUser(userId);
        Language entity = languageMapper.toEntity(request);
        entity.setUser(user);
        return languageMapper.toResponse(languageRepository.save(entity));
    }

    @Transactional
    public LanguageResponse updateLanguage(UUID userId, UUID id, LanguageRequest request) {
        Language entity = languageRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Language", id));
        languageMapper.updateFromRequest(request, entity);
        return languageMapper.toResponse(languageRepository.save(entity));
    }

    @Transactional
    public void deleteLanguage(UUID userId, UUID id) {
        Language entity = languageRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Language", id));
        languageRepository.delete(entity);
    }

    // ===== Helper =====

    private User getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }
}
