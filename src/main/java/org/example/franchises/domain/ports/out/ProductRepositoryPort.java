package org.example.franchises.domain.ports.out;

import org.example.franchises.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findByBranchId(Long branchId);

    List<Product> findByBranchIdIn(List<Long> branchIds);

    void deleteById(Long id);

    boolean existsById(Long id);
}
