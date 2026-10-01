package org.example.franchises.infrastructure.web;

import lombok.RequiredArgsConstructor;
import org.example.franchises.domain.model.Branch;
import org.example.franchises.domain.model.Franchise;
import org.example.franchises.domain.model.Product;
import org.example.franchises.domain.model.dto.HighestStockProductDto;
import org.example.franchises.domain.ports.in.FranchiseCommandUseCase;
import org.example.franchises.domain.ports.in.FranchiseQueryUseCase;
import org.example.franchises.infrastructure.web.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseCommandUseCase commandUseCase;
    private final FranchiseQueryUseCase queryUseCase;

    @PostMapping("/franchises")
    public ResponseEntity<FranchiseResponse> createFranchise(@RequestBody CreateFranchiseRequest request) {
        Franchise franchise = commandUseCase.createFranchise(request.name());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new FranchiseResponse(franchise.getId(), franchise.getName()));
    }

    @GetMapping("/franchises")
    public ResponseEntity<List<FranchiseResponse>> getAllFranchises() {
        List<FranchiseResponse> responses = queryUseCase.getAllFranchises().stream()
                .map(f -> new FranchiseResponse(f.getId(), f.getName()))
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/franchises/{franchiseId}")
    public ResponseEntity<FranchiseResponse> getFranchiseById(@PathVariable Long franchiseId) {
        Franchise f = queryUseCase.getFranchiseById(franchiseId);
        return ResponseEntity.ok(new FranchiseResponse(f.getId(), f.getName()));
    }

    @PatchMapping("/franchises/{franchiseId}/name")
    public ResponseEntity<FranchiseResponse> updateFranchiseName(
            @PathVariable Long franchiseId,
            @RequestBody UpdateNameRequest request) {
        Franchise franchise = commandUseCase.updateFranchiseName(franchiseId, request.name());
        return ResponseEntity.ok(new FranchiseResponse(franchise.getId(), franchise.getName()));
    }

    @PostMapping("/franchises/{franchiseId}/branches")
    public ResponseEntity<BranchResponse> addBranch(
            @PathVariable Long franchiseId,
            @RequestBody CreateBranchRequest request) {
        Branch branch = commandUseCase.addBranchToFranchise(franchiseId, request.name());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BranchResponse(branch.getId(), branch.getName(), branch.getFranchiseId()));
    }

    @GetMapping("/franchises/{franchiseId}/branches")
    public ResponseEntity<List<BranchResponse>> getBranchesByFranchise(@PathVariable Long franchiseId) {
        List<BranchResponse> responses = queryUseCase.getBranchesByFranchiseId(franchiseId).stream()
                .map(b -> new BranchResponse(b.getId(), b.getName(), b.getFranchiseId()))
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/branches/{branchId}/name")
    public ResponseEntity<BranchResponse> updateBranchName(
            @PathVariable Long branchId,
            @RequestBody UpdateNameRequest request) {
        Branch branch = commandUseCase.updateBranchName(branchId, request.name());
        return ResponseEntity.ok(new BranchResponse(branch.getId(), branch.getName(), branch.getFranchiseId()));
    }

    @PostMapping("/branches/{branchId}/products")
    public ResponseEntity<ProductResponse> addProduct(
            @PathVariable Long branchId,
            @RequestBody CreateProductRequest request) {
        Product product = commandUseCase.addProductToBranch(branchId, request.name(), request.stock());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ProductResponse(product.getId(), product.getName(), product.getStock(), product.getBranchId()));
    }

    @GetMapping("/branches/{branchId}/products")
    public ResponseEntity<List<ProductResponse>> getProductsByBranch(@PathVariable Long branchId) {
        List<ProductResponse> responses = queryUseCase.getProductsByBranch(branchId).stream()
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getStock(), p.getBranchId()))
                .toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/branches/{branchId}/products/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long branchId,
            @PathVariable Long productId) {
        commandUseCase.deleteProductFromBranch(branchId, productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/products/{productId}/stock")
    public ResponseEntity<ProductResponse> updateProductStock(
            @PathVariable Long productId,
            @RequestBody UpdateStockRequest request) {
        Product product = commandUseCase.updateProductStock(productId, request.stock());
        return ResponseEntity.ok(new ProductResponse(product.getId(), product.getName(), product.getStock(), product.getBranchId()));
    }

    @PatchMapping("/products/{productId}/name")
    public ResponseEntity<ProductResponse> updateProductName(
            @PathVariable Long productId,
            @RequestBody UpdateNameRequest request) {
        Product product = commandUseCase.updateProductName(productId, request.name());
        return ResponseEntity.ok(new ProductResponse(product.getId(), product.getName(), product.getStock(), product.getBranchId()));
    }

    @GetMapping("/franchises/{franchiseId}/max-stock-products")
    public ResponseEntity<List<MaxStockProductResponse>> getMaxStockProducts(@PathVariable Long franchiseId) {
        List<HighestStockProductDto> dtos = queryUseCase.getMaxStockProductsPerBranch(franchiseId);
        List<MaxStockProductResponse> responses = dtos.stream()
                .map(dto -> new MaxStockProductResponse(
                        dto.branchId(),
                        dto.branchName(),
                        dto.productId(),
                        dto.productName(),
                        dto.stock()
                ))
                .toList();
        return ResponseEntity.ok(responses);
    }
}
