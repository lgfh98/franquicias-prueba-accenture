package org.example.franchises.infrastructure.persistence.mapper;

import org.example.franchises.domain.model.Branch;
import org.example.franchises.infrastructure.persistence.entity.BranchJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class BranchPersistenceMapper {

    public BranchJpaEntity toJpaEntity(Branch domain) {
        return new BranchJpaEntity(domain.getId(), domain.getFranchiseId(), domain.getName());
    }

    public Branch toDomain(BranchJpaEntity entity) {
        return new Branch(entity.getId(), entity.getFranchiseId(), entity.getName());
    }
}
