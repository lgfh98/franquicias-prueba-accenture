package org.example.franchises.infrastructure.persistence.mapper;

import org.example.franchises.domain.model.Product;
import org.example.franchises.infrastructure.persistence.entity.ProductJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceMapper {

    public ProductJpaEntity toJpaEntity(Product domain) {
        return new ProductJpaEntity(domain.getId(), domain.getBranchId(), domain.getName(), domain.getStock());
    }

    public Product toDomain(ProductJpaEntity entity) {
        return new Product(entity.getId(), entity.getBranchId(), entity.getName(), entity.getStock());
    }
}
