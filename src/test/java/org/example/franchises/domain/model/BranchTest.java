package org.example.franchises.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BranchTest {

    @Test
    void shouldCreateBranchSuccessfully() {
        // Arrange & Act
        Branch branch = new Branch(1L, 10L, "Sucursal Centro");

        // Assert
        assertEquals(1L, branch.getId());
        assertEquals(10L, branch.getFranchiseId());
        assertEquals("Sucursal Centro", branch.getName());
    }

    @Test
    void shouldCreateBranchWithoutId() {
        // Arrange & Act
        Branch branch = new Branch(10L, " Sucursal Norte ");

        // Assert
        assertNull(branch.getId());
        assertEquals(10L, branch.getFranchiseId());
        assertEquals("Sucursal Norte", branch.getName());
    }

    @Test
    void shouldThrowExceptionWhenFranchiseIdIsNull() {
        // Arrange, Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Branch(null, "Sucursal Sur")
        );
        assertEquals("FranchisesId cannot be null", ex.getMessage());
    }

    @Test
    void shouldUpdateNameSuccessfully() {
        // Arrange
        Branch branch = new Branch(1L, 10L, "Sucursal Centro");

        // Act
        branch.updateName(" Sucursal Principal ");

        // Assert
        assertEquals("Sucursal Principal", branch.getName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingToBlankName() {
        // Arrange
        Branch branch = new Branch(1L, 10L, "Sucursal Centro");

        // Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> branch.updateName("   ")
        );
        assertEquals("New name cannot be null or blank", ex.getMessage());
    }
}
