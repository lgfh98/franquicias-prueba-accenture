package org.example.franchises.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.example.franchises.domain.model.Product;
import org.example.franchises.domain.ports.out.ProductRepositoryPort;
import org.example.franchises.infrastructure.persistence.entity.ProductJpaEntity;
import org.example.franchises.infrastructure.persistence.mapper.ProductPersistenceMapper;
import org.example.franchises.infrastructure.persistence.repository.SpringDataProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository repository;
    private final ProductPersistenceMapper mapper;

    @Override
    public Product save(Product product) {
        ProductJpaEntity saved = repository.save(mapper.toJpaEntity(product));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Product> findByBranchId(Long branchId) {
        return repository.findByBranchId(branchId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Product> findByBranchIdIn(List<Long> branchIds) {
        return repository.findByBranchIdIn(branchIds).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
