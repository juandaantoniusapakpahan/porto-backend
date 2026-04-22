package com.portfolio.service;

import com.portfolio.dto.response.*;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.*;
import com.portfolio.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublicPortfolioService {

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

    private final UserMapper userMapper;
    private final PersonalInfoMapper personalInfoMapper;
    private final ProfessionalSummaryMapper summaryMapper;
    private final WorkExperienceMapper workExperienceMapper;
    private final EducationMapper educationMapper;
    private final SkillMapper skillMapper;
    private final CertificationMapper certificationMapper;
    private final ProjectMapper projectMapper;
    private final AwardMapper awardMapper;
    private final LanguageMapper languageMapper;

    @Transactional(readOnly = true)
    public PublicPortfolioResponse getPortfolioByUsername(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found for username: " + username));

        var userId = user.getId();

        return new PublicPortfolioResponse(
                userMapper.toResponse(user),
                personalInfoRepository.findByUserId(userId)
                        .map(personalInfoMapper::toResponse).orElse(null),
                summaryRepository.findByUserId(userId)
                        .map(summaryMapper::toResponse).orElse(null),
                workExperienceMapper.toResponseList(workExperienceRepository.findByUserIdOrderByOrderIndexAsc(userId)),
                educationMapper.toResponseList(educationRepository.findByUserIdOrderByOrderIndexAsc(userId)),
                skillMapper.toResponseList(skillRepository.findByUserIdOrderByOrderIndexAsc(userId)),
                certificationMapper.toResponseList(certificationRepository.findByUserIdOrderByOrderIndexAsc(userId)),
                projectMapper.toResponseList(projectRepository.findByUserIdOrderByOrderIndexAsc(userId)),
                awardMapper.toResponseList(awardRepository.findByUserIdOrderByOrderIndexAsc(userId)),
                languageMapper.toResponseList(languageRepository.findByUserIdOrderByOrderIndexAsc(userId))
        );
    }
}
