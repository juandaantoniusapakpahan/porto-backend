package com.portfolio.repository;

import com.portfolio.entity.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, UUID> {

    List<WorkExperience> findByUserIdOrderByOrderIndexAsc(UUID userId);

    Optional<WorkExperience> findByIdAndUserId(UUID id, UUID userId);
}
