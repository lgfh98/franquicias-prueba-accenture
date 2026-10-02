package org.example.franchises.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FranchiseTest {

    @Test
    void shouldCreateFranchiseSuccessfully() {
        // Arrange & Act
        Franchise franchise = new Franchise(1L, "Franquicia Demo");

        // Assert
        assertEquals(1L, franchise.getId());
        assertEquals("Franquicia Demo", franchise.getName());
    }

    @Test
    void shouldCreateFranchiseWithoutId() {
        // Arrange & Act
        Franchise franchise = new Franchise("Franquicia Nueva");

        // Assert
        assertNull(franchise.getId());
        assertEquals("Franquicia Nueva", franchise.getName());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlankOnCreation() {
        // Arrange, Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Franchise("   ")
        );
        assertEquals("Franchise name cannot be null or blank", ex.getMessage());
    }

    @Test
    void shouldUpdateNameSuccessfully() {
        // Arrange
        Franchise franchise = new Franchise(1L, "Nombre Anterior");

        // Act
        franchise.updateName(" Nombre Nuevo ");

        // Assert
        assertEquals("Nombre Nuevo", franchise.getName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingToBlankName() {
        // Arrange
        Franchise franchise = new Franchise(1L, "Nombre Anterior");

        // Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> franchise.updateName("   ")
        );
        assertEquals("Franchise new name cannot be null or blank", ex.getMessage());
    }
}
