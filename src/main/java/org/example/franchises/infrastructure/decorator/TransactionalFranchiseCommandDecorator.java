package org.example.franchises.infrastructure.decorator;

import lombok.RequiredArgsConstructor;
import org.example.franchises.domain.model.Branch;
import org.example.franchises.domain.model.Franchise;
import org.example.franchises.domain.model.Product;
import org.example.franchises.domain.ports.in.FranchiseCommandUseCase;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class TransactionalFranchiseCommandDecorator implements FranchiseCommandUseCase {

    private final FranchiseCommandUseCase delegate;

    @Override
    public Franchise createFranchise(String name) {
        return delegate.createFranchise(name);
    }

    @Override
    public Branch addBranchToFranchise(Long franchiseId, String branchName) {
        return delegate.addBranchToFranchise(franchiseId, branchName);
    }

    @Override
    public Product addProductToBranch(Long branchId, String productName, Integer stock) {
        return delegate.addProductToBranch(branchId, productName, stock);
    }

    @Override
    public void deleteProductFromBranch(Long branchId, Long productId) {
        delegate.deleteProductFromBranch(branchId, productId);
    }

    @Override
    public Product updateProductStock(Long productId, Integer newStock) {
        return delegate.updateProductStock(productId, newStock);
    }

    @Override
    public Franchise updateFranchiseName(Long franchiseId, String newName) {
        return delegate.updateFranchiseName(franchiseId, newName);
    }

    @Override
    public Branch updateBranchName(Long branchId, String newName) {
        return delegate.updateBranchName(branchId, newName);
    }

    @Override
    public Product updateProductName(Long productId, String newName) {
        return delegate.updateProductName(productId, newName);
    }
}
