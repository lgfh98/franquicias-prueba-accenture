package org.example.franchises.infrastructure.decorator;

import lombok.RequiredArgsConstructor;
import org.example.franchises.domain.model.Branch;
import org.example.franchises.domain.model.Franchise;
import org.example.franchises.domain.model.dto.HighestStockProductDto;
import org.example.franchises.domain.ports.in.FranchiseQueryUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadOnlyFranchiseQueryDecorator implements FranchiseQueryUseCase {

    private final FranchiseQueryUseCase delegate;

    @Override
    public List<HighestStockProductDto> getMaxStockProductsPerBranch(Long franchiseId) {
        return delegate.getMaxStockProductsPerBranch(franchiseId);
    }

    @Override
    public List<Franchise> getAllFranchises() {
        return delegate.getAllFranchises();
    }

    @Override
    public Franchise getFranchiseById(Long franchiseId) {
        return delegate.getFranchiseById(franchiseId);
    }

    @Override
    public List<Branch> getBranchesByFranchiseId(Long franchiseId) {
        return delegate.getBranchesByFranchiseId(franchiseId);
    }
}
