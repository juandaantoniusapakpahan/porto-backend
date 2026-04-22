package com.portfolio.repository;

import com.portfolio.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, UUID> {

    List<Certification> findByUserIdOrderByOrderIndexAsc(UUID userId);

    Optional<Certification> findByIdAndUserId(UUID id, UUID userId);
}
