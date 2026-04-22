package com.portfolio.repository;

import com.portfolio.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AwardRepository extends JpaRepository<Award, UUID> {

    List<Award> findByUserIdOrderByOrderIndexAsc(UUID userId);

    Optional<Award> findByIdAndUserId(UUID id, UUID userId);
}
