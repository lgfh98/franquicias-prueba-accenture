package org.example.franchises.domain.ports.in;

import org.example.franchises.domain.model.Branch;
import org.example.franchises.domain.model.Franchise;
import org.example.franchises.domain.model.Product;

public interface FranchiseCommandUseCase {
    Franchise createFranchise(String name);

    Franchise updateFranchiseName(Long franchiseId, String newName);

    Branch addBranchToFranchise(Long franchiseId, String name);

    Branch updateBranchName(Long branchId, String newName);

    Product addProductToBranch(Long branchId, String name, Integer stock);

    void deleteProductFromBranch(Long branchId, Long productId);

    Product updateProductStock(Long productId, Integer newStock);

    Product updateProductName(Long productId, String newName);
}
