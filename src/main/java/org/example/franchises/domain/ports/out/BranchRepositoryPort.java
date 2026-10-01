package org.example.franchises.domain.ports.out;

import org.example.franchises.domain.model.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchRepositoryPort {
    Branch save(Branch branch);

    Optional<Branch> findById(Long id);

    List<Branch> findByFranchiseId(Long franchiseId);

    boolean existsById(Long id);
}
