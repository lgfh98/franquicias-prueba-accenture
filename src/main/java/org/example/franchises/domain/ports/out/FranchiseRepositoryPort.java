package org.example.franchises.domain.ports.out;

import org.example.franchises.domain.model.Franchise;

import java.util.List;
import java.util.Optional;

public interface FranchiseRepositoryPort {
    Franchise save(Franchise franquicia);

    Optional<Franchise> findById(Long id);

    List<Franchise> findAll();

    boolean existsById(Long id);
}
