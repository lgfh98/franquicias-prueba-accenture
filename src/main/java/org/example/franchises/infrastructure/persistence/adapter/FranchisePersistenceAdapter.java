package org.example.franchises.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.example.franchises.domain.model.Franchise;
import org.example.franchises.domain.ports.out.FranchiseRepositoryPort;
import org.example.franchises.infrastructure.persistence.entity.FranchiseJpaEntity;
import org.example.franchises.infrastructure.persistence.mapper.FranchisePersistenceMapper;
import org.example.franchises.infrastructure.persistence.repository.SpringDataFranchiseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FranchisePersistenceAdapter implements FranchiseRepositoryPort {

    private final SpringDataFranchiseRepository repository;
    private final FranchisePersistenceMapper mapper;

    @Override
    public Franchise save(Franchise franchise) {
        FranchiseJpaEntity saved = repository.save(mapper.toJpaEntity(franchise));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Franchise> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Franchise> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
