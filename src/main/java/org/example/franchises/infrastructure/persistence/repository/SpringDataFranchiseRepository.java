package org.example.franchises.infrastructure.persistence.repository;

import org.example.franchises.infrastructure.persistence.entity.FranchiseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataFranchiseRepository extends JpaRepository<FranchiseJpaEntity, Long> {
}
