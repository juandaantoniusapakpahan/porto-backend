package com.portfolio.repository;

import com.portfolio.entity.ProfessionalSummary;
import com.portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfessionalSummaryRepository extends JpaRepository<ProfessionalSummary, UUID> {

    Optional<ProfessionalSummary> findByUser(User user);

    Optional<ProfessionalSummary> findByUserId(UUID userId);
}
