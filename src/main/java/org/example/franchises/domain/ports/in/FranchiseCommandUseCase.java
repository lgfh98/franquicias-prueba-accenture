package org.example.franchises.domain.ports.in;

import org.example.franchises.domain.model.Branch;
import org.example.franchises.domain.model.Franchise;
import org.example.franchises.domain.model.Product;

public interface FranchiseCommandUseCase {
    Franchise createFranchise(String name);

    Franchise updateFranchiseName(Long franchiseId, String newName);

    Branch addBranch(Long franchiseId, String name);

    Branch updateBranchName(Long branchId, String newName);

    Product addProduct(Long branchId, String name, Integer stock);

    void deleteProduct(Long branchId, Long productId);

    Product updateProductStock(Long productId, Integer newStock);

    Product updateProductName(Long productId, String newName);
}
