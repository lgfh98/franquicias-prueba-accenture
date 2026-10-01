package org.example.franchises.infrastructure.persistence.repository;

import org.example.franchises.infrastructure.persistence.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataProductRepository extends JpaRepository<ProductJpaEntity, Long> {
    List<ProductJpaEntity> findByBranchId(Long branchId);

    List<ProductJpaEntity> findByBranchIdIn(List<Long> branchIds);
}
