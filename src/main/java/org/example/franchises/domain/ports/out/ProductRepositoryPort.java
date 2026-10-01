package org.example.franchises.domain.ports.out;

import org.example.franchises.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product producto);

    Optional<Product> findById(Long id);

    List<Product> findBySucursalId(Long sucursalId);

    List<Product> findBySucursalIdIn(List<Long> sucursalIds);

    void deleteById(Long id);

    boolean existsById(Long id);
}
