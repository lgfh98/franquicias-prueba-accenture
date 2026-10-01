package org.example.franchises.infrastructure.persistence.mapper;

import org.example.franchises.domain.model.Franchise;
import org.example.franchises.infrastructure.persistence.entity.FranchiseJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class FranchisePersistenceMapper {

    public FranchiseJpaEntity toJpaEntity(Franchise domain) {
        return new FranchiseJpaEntity(domain.getId(), domain.getName());
    }

    public Franchise toDomain(FranchiseJpaEntity entity) {
        return new Franchise(entity.getId(), entity.getName());
    }
}
