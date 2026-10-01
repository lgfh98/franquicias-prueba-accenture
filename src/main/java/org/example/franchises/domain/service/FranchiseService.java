package org.example.franchises.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.franchises.domain.model.Branch;
import org.example.franchises.domain.model.Franchise;
import org.example.franchises.domain.model.Product;
import org.example.franchises.domain.model.dto.HighestStockProductDto;
import org.example.franchises.domain.ports.in.FranchiseCommandUseCase;
import org.example.franchises.domain.ports.in.FranchiseQueryUseCase;
import org.example.franchises.domain.ports.out.BranchRepositoryPort;
import org.example.franchises.domain.ports.out.FranchiseRepositoryPort;
import org.example.franchises.domain.ports.out.ProductRepositoryPort;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FranchiseService implements FranchiseCommandUseCase, FranchiseQueryUseCase {

    private final FranchiseRepositoryPort franchiseRepository;
    private final BranchRepositoryPort branchRepository;
    private final ProductRepositoryPort productRepository;

    @Override
    public Franchise createFranchise(String name) {
        return franchiseRepository.save(new Franchise(name));
    }

    @Override
    public Franchise updateFranchiseName(Long franchiseId, String newName) {
        Franchise franchise = getFranchiseById(franchiseId);
        franchise.updateName(newName);
        return franchiseRepository.save(franchise);
    }

    @Override
    public Branch addBranchToFranchise(Long franchiseId, String name) {
        if (!franchiseRepository.existsById(franchiseId)) {
            throw new NoSuchElementException("Franchise not found with ID: " + franchiseId);
        }
        return branchRepository.save(new Branch(franchiseId, name));
    }

    @Override
    public Branch updateBranchName(Long branchId, String newName) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("Branch not found with ID: " + branchId));
        branch.updateName(newName);
        return branchRepository.save(branch);
    }

    @Override
    public Product addProductToBranch(Long branchId, String name, Integer stock) {
        if (!branchRepository.existsById(branchId)) {
            throw new NoSuchElementException("Branch not found with ID: " + branchId);
        }
        return productRepository.save(new Product(branchId, name, stock));
    }

    @Override
    public void deleteProductFromBranch(Long branchId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));

        if (!Objects.equals(product.getBranchId(), branchId)) {
            throw new IllegalArgumentException("The product with ID " + productId + " It does not belong to the branch. " + branchId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public Product updateProductStock(Long productId, Integer newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
        product.updateStock(newStock);
        return productRepository.save(product);
    }

    @Override
    public Product updateProductName(Long productId, String newName) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
        product.updateName(newName);
        return productRepository.save(product);
    }

    @Override
    public Franchise getFranchiseById(Long id) {
        return franchiseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Franchise not found with ID: " + id));
    }

    @Override
    public List<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }

    @Override
    public List<Branch> getBranchesByFranchiseId(Long franchiseId) {
        if (!franchiseRepository.existsById(franchiseId)) {
            throw new NoSuchElementException("Franchise not found with ID: " + franchiseId);
        }
        return branchRepository.findByFranchiseId(franchiseId);
    }

    @Override
    public List<HighestStockProductDto> getMaxStockProductsPerBranch(Long franchiseId) {
        List<Branch> branches = getBranchesByFranchiseId(franchiseId);
        if (branches.isEmpty()) {
            return List.of();
        }

        Map<Long, Branch> sucursalMap = branches.stream()
                .collect(Collectors.toMap(Branch::getId, s -> s));

        List<Long> sucursalIds = branches.stream().map(Branch::getId).toList();
        List<Product> productos = productRepository.findByBranchIdIn(sucursalIds);

        Map<Long, Product> maxProductoPorSucursal = productos.stream()
                .collect(Collectors.toMap(
                        Product::getBranchId,
                        p -> p,
                        BinaryOperator.maxBy(Comparator.comparingInt(Product::getStock))
                ));

        return maxProductoPorSucursal.entrySet().stream()
                .map(entry -> {
                    Branch branch = sucursalMap.get(entry.getKey());
                    Product product = entry.getValue();
                    return new HighestStockProductDto(
                            branch.getId(),
                            branch.getName(),
                            product.getId(),
                            product.getName(),
                            product.getStock()
                    );
                })
                .sorted(Comparator.comparing(HighestStockProductDto::branchName))
                .toList();
    }
}

