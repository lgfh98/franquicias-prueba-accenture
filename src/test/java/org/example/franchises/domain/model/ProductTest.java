package org.example.franchises.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductWhenDataIsValid() {
        // Arrange & Act
        Product product = new Product(1L, 10L, "Café Latte", 50);

        // Assert
        assertEquals(1L, product.getId());
        assertEquals(10L, product.getBranchId());
        assertEquals("Café Latte", product.getName());
        assertEquals(50, product.getStock());
    }

    @Test
    void shouldThrowExceptionWhenStockIsNegativeOnCreation() {
        // Arrange, Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Product(1L, "Café", -5)
        );
        assertEquals("The stock must be an integer greater than or equal to 0.", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlankOnCreation() {
        // Arrange, Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Product(1L, "   ", 10)
        );
        assertEquals("The product name cannot be empty.", ex.getMessage());
    }

    @Test
    void shouldUpdateStockSuccessfully() {
        // Arrange
        Product product = new Product(1L, 10L, "Café", 20);

        // Act
        product.updateStock(80);

        // Assert
        assertEquals(80, product.getStock());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingToNegativeStock() {
        // Arrange
        Product product = new Product(1L, 10L, "Café", 20);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> product.updateStock(-1));
    }

    @Test
    void shouldThrowExceptionWhenBranchIdIsNull() {
        // Arrange, Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Product(null, "Café", 10)
        );
        assertEquals("The branch ID is mandatory.", ex.getMessage());
    }

    @Test
    void shouldUpdateNameSuccessfully() {
        // Arrange
        Product product = new Product(1L, 10L, "Café", 20);

        // Act
        product.updateName("Café Especial");

        // Assert
        assertEquals("Café Especial", product.getName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingToBlankName() {
        // Arrange
        Product product = new Product(1L, 10L, "Café", 20);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> product.updateName("   "));
    }
}
