package org.example.franchises.domain.service;

import org.example.franchises.domain.model.Branch;
import org.example.franchises.domain.model.Franchise;
import org.example.franchises.domain.model.Product;
import org.example.franchises.domain.model.dto.HighestStockProductDto;
import org.example.franchises.domain.ports.out.BranchRepositoryPort;
import org.example.franchises.domain.ports.out.FranchiseRepositoryPort;
import org.example.franchises.domain.ports.out.ProductRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceTest {

    @Mock
    private FranchiseRepositoryPort franchiseRepository;

    @Mock
    private BranchRepositoryPort branchRepository;

    @Mock
    private ProductRepositoryPort productRepository;

    @InjectMocks
    private FranchiseService franchiseService;

    // --- Franchise Tests ---

    @Test
    void shouldCreateFranchiseSuccessfully() {
        // Arrange
        Franchise expected = new Franchise(1L, "Café Central");
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(expected);

        // Act
        Franchise created = franchiseService.createFranchise("Café Central");

        // Assert
        assertNotNull(created);
        assertEquals(1L, created.getId());
        assertEquals("Café Central", created.getName());
        verify(franchiseRepository).save(any(Franchise.class));
    }

    @Test
    void shouldUpdateFranchiseNameSuccessfully() {
        // Arrange
        Long franchiseId = 1L;
        Franchise existing = new Franchise(franchiseId, "Café Central");
        when(franchiseRepository.findById(franchiseId)).thenReturn(Optional.of(existing));
        when(franchiseRepository.save(any(Franchise.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Franchise updated = franchiseService.updateFranchiseName(franchiseId, "Café Gourmet");

        // Assert
        assertEquals("Café Gourmet", updated.getName());
        verify(franchiseRepository).save(existing);
    }

    @Test
    void shouldGetFranchiseByIdWhenExists() {
        // Arrange
        Long franchiseId = 1L;
        Franchise franchise = new Franchise(franchiseId, "Café Central");
        when(franchiseRepository.findById(franchiseId)).thenReturn(Optional.of(franchise));

        // Act
        Franchise result = franchiseService.getFranchiseById(franchiseId);

        // Assert
        assertEquals(franchiseId, result.getId());
        assertEquals("Café Central", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenFranchiseByIdNotFound() {
        // Arrange
        when(franchiseRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException ex = assertThrows(
                NoSuchElementException.class,
                () -> franchiseService.getFranchiseById(999L)
        );
        assertEquals("Franchise not found with ID: 999", ex.getMessage());
    }

    @Test
    void shouldReturnAllFranchises() {
        // Arrange
        List<Franchise> franchises = List.of(
                new Franchise(1L, "Franquicia A"),
                new Franchise(2L, "Franquicia B")
        );
        when(franchiseRepository.findAll()).thenReturn(franchises);

        // Act
        List<Franchise> result = franchiseService.getAllFranchises();

        // Assert
        assertEquals(2, result.size());
    }

    // --- Branch Tests ---

    @Test
    void shouldAddBranchToFranchiseSuccessfully() {
        // Arrange
        Long franchiseId = 1L;
        Branch expected = new Branch(10L, franchiseId, "Sucursal Norte");
        when(franchiseRepository.existsById(franchiseId)).thenReturn(true);
        when(branchRepository.save(any(Branch.class))).thenReturn(expected);

        // Act
        Branch branch = franchiseService.addBranchToFranchise(franchiseId, "Sucursal Norte");

        // Assert
        assertNotNull(branch);
        assertEquals(10L, branch.getId());
        assertEquals("Sucursal Norte", branch.getName());
        verify(branchRepository).save(any(Branch.class));
    }

    @Test
    void shouldThrowExceptionWhenAddingBranchToNonExistingFranchise() {
        // Arrange
        Long nonExistingId = 99L;
        when(franchiseRepository.existsById(nonExistingId)).thenReturn(false);

        // Act & Assert
        NoSuchElementException ex = assertThrows(
                NoSuchElementException.class,
                () -> franchiseService.addBranchToFranchise(nonExistingId, "Sucursal Norte")
        );
        assertEquals("Franchise not found with ID: 99", ex.getMessage());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void shouldUpdateBranchNameSuccessfully() {
        // Arrange
        Long branchId = 10L;
        Branch branch = new Branch(branchId, 1L, "Sucursal Antigua");
        when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));
        when(branchRepository.save(any(Branch.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Branch updated = franchiseService.updateBranchName(branchId, "Sucursal Moderna");

        // Assert
        assertEquals("Sucursal Moderna", updated.getName());
        verify(branchRepository).save(branch);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingBranchNameNotFound() {
        // Arrange
        when(branchRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> franchiseService.updateBranchName(99L, "Nueva"));
    }

    @Test
    void shouldGetBranchesByFranchiseId() {
        // Arrange
        Long franchiseId = 1L;
        List<Branch> branches = List.of(new Branch(10L, franchiseId, "Sucursal 1"));
        when(franchiseRepository.existsById(franchiseId)).thenReturn(true);
        when(branchRepository.findByFranchiseId(franchiseId)).thenReturn(branches);

        // Act
        List<Branch> result = franchiseService.getBranchesByFranchiseId(franchiseId);

        // Assert
        assertEquals(1, result.size());
    }

    // --- Product Tests ---

    @Test
    void shouldAddProductToBranchSuccessfully() {
        // Arrange
        Long branchId = 10L;
        Product expected = new Product(100L, branchId, "Café Latte", 25);
        when(branchRepository.existsById(branchId)).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(expected);

        // Act
        Product product = franchiseService.addProductToBranch(branchId, "Café Latte", 25);

        // Assert
        assertNotNull(product);
        assertEquals(100L, product.getId());
        assertEquals("Café Latte", product.getName());
        assertEquals(25, product.getStock());
    }

    @Test
    void shouldThrowExceptionWhenAddingProductToNonExistingBranch() {
        // Arrange
        Long nonExistingBranchId = 99L;
        when(branchRepository.existsById(nonExistingBranchId)).thenReturn(false);

        // Act & Assert
        assertThrows(
                NoSuchElementException.class,
                () -> franchiseService.addProductToBranch(nonExistingBranchId, "Café", 10)
        );
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldDeleteProductFromBranchSuccessfully() {
        // Arrange
        Long branchId = 10L;
        Long productId = 100L;
        Product product = new Product(productId, branchId, "Café Latte", 20);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        franchiseService.deleteProductFromBranch(branchId, productId);

        // Assert
        verify(productRepository).deleteById(productId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {
        // Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                NoSuchElementException.class,
                () -> franchiseService.deleteProductFromBranch(10L, 999L)
        );
        verify(productRepository, never()).deleteById(any());
    }

    @Test
    void shouldThrowExceptionWhenDeletingProductFromWrongBranch() {
        // Arrange
        Long productId = 5L;
        Long correctBranchId = 1L;
        Long wrongBranchId = 2L;
        Product product = new Product(productId, correctBranchId, "Pastel", 10);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> franchiseService.deleteProductFromBranch(wrongBranchId, productId)
        );
        assertTrue(ex.getMessage().contains("does not belong to the branch"));
        verify(productRepository, never()).deleteById(any());
    }

    @Test
    void shouldUpdateProductStockSuccessfully() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = new Product(productId, 10L, "Café Latte", 20);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Product updated = franchiseService.updateProductStock(productId, 150);

        // Assert
        assertEquals(150, updated.getStock());
        verify(productRepository).save(existingProduct);
    }

    @Test
    void shouldUpdateProductNameSuccessfully() {
        // Arrange
        Long productId = 1L;
        Product existing = new Product(productId, 10L, "Café Latte", 20);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Product updated = franchiseService.updateProductName(productId, "Café Mocha");

        // Assert
        assertEquals("Café Mocha", updated.getName());
        verify(productRepository).save(existing);
    }

    @Test
    void shouldGetProductsByBranch() {
        // Arrange
        Long branchId = 10L;
        List<Product> products = List.of(new Product(1L, branchId, "Café", 10));
        when(branchRepository.existsById(branchId)).thenReturn(true);
        when(productRepository.findByBranchId(branchId)).thenReturn(products);

        // Act
        List<Product> result = franchiseService.getProductsByBranch(branchId);

        // Assert
        assertEquals(1, result.size());
    }

    // --- Query: Max Stock Products ---

    @Test
    void shouldReturnHighestStockProductPerBranch() {
        // Arrange
        Long franchiseId = 1L;
        Branch branch1 = new Branch(1L, franchiseId, "Sucursal Norte");
        Branch branch2 = new Branch(2L, franchiseId, "Sucursal Sur");

        Product product1 = new Product(1L, 1L, "Café Latte", 20);
        Product product2 = new Product(2L, 1L, "Café Americano", 80);
        Product product3 = new Product(3L, 2L, "Cold Brew", 110);
        Product product4 = new Product(4L, 2L, "Mocaccino", 40);

        when(franchiseRepository.existsById(franchiseId)).thenReturn(true);
        when(branchRepository.findByFranchiseId(franchiseId)).thenReturn(List.of(branch1, branch2));
        when(productRepository.findByBranchIdIn(List.of(1L, 2L)))
                .thenReturn(List.of(product1, product2, product3, product4));

        // Act
        List<HighestStockProductDto> result = franchiseService.getMaxStockProductsPerBranch(franchiseId);

        // Assert
        assertEquals(2, result.size());

        HighestStockProductDto topBranch1 = result.stream()
                .filter(dto -> dto.branchId().equals(1L))
                .findFirst()
                .orElseThrow();
        assertEquals("Café Americano", topBranch1.productName());
        assertEquals(80, topBranch1.stock());

        HighestStockProductDto topBranch2 = result.stream()
                .filter(dto -> dto.branchId().equals(2L))
                .findFirst()
                .orElseThrow();
        assertEquals("Cold Brew", topBranch2.productName());
        assertEquals(110, topBranch2.stock());
    }

    @Test
    void shouldReturnEmptyListWhenFranchiseHasNoBranches() {
        // Arrange
        Long franchiseId = 1L;
        when(franchiseRepository.existsById(franchiseId)).thenReturn(true);
        when(branchRepository.findByFranchiseId(franchiseId)).thenReturn(List.of());

        // Act
        List<HighestStockProductDto> result = franchiseService.getMaxStockProductsPerBranch(franchiseId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenFranchiseDoesNotExist() {
        // Arrange
        Long nonExistingId = 999L;
        when(franchiseRepository.existsById(nonExistingId)).thenReturn(false);

        // Act & Assert
        NoSuchElementException ex = assertThrows(
                NoSuchElementException.class,
                () -> franchiseService.getMaxStockProductsPerBranch(nonExistingId)
        );
        assertEquals("Franchise not found with ID: 999", ex.getMessage());
    }
}
