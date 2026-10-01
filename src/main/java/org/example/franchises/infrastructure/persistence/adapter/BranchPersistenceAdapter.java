package org.example.franchises.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.example.franchises.domain.model.Branch;
import org.example.franchises.domain.ports.out.BranchRepositoryPort;
import org.example.franchises.infrastructure.persistence.entity.BranchJpaEntity;
import org.example.franchises.infrastructure.persistence.mapper.BranchPersistenceMapper;
import org.example.franchises.infrastructure.persistence.repository.SpringDataBranchRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BranchPersistenceAdapter implements BranchRepositoryPort {

    private final SpringDataBranchRepository repository;
    private final BranchPersistenceMapper mapper;

    @Override
    public Branch save(Branch branch) {
        BranchJpaEntity saved = repository.save(mapper.toJpaEntity(branch));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Branch> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Branch> findByFranchiseId(Long franchiseId) {
        return repository.findByFranchiseId(franchiseId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
