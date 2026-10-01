package org.example.franchises.infrastructure.persistence.repository;

import org.example.franchises.infrastructure.persistence.entity.BranchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataBranchRepository extends JpaRepository<BranchJpaEntity, Long> {
    List<BranchJpaEntity> findByFranchiseId(Long franchiseId);
}
