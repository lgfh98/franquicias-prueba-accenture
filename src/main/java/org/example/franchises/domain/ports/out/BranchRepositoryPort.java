package org.example.franchises.domain.ports.out;

import org.example.franchises.domain.model.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchRepositoryPort {
    Branch save(Branch sucursal);

    Optional<Branch> findById(Long id);

    List<Branch> findByFranquiciaId(Long franquiciaId);

    boolean existsById(Long id);
}
