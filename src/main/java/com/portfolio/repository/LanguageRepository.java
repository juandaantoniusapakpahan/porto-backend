package com.portfolio.repository;

import com.portfolio.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {

    List<Language> findByUserIdOrderByOrderIndexAsc(UUID userId);

    Optional<Language> findByIdAndUserId(UUID id, UUID userId);
}
