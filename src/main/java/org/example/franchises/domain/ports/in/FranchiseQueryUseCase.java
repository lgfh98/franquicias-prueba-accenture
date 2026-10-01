package org.example.franchises.domain.ports.in;

import org.example.franchises.domain.model.Branch;
import org.example.franchises.domain.model.Franchise;
import org.example.franchises.domain.model.Product;
import org.example.franchises.domain.model.dto.HighestStockProductDto;

import java.util.List;

public interface FranchiseQueryUseCase {
    Franchise getFranchiseById(Long id);

    List<Franchise> getAllFranchises();

    List<Branch> getBranchesByFranchiseId(Long franchiseId);

    List<Product> getProductsByBranch(Long branchId);

    List<HighestStockProductDto> getMaxStockProductsPerBranch(Long franchiseId);
}
