package org.example.franchises.domain.model;

import lombok.Getter;

public class Product {

    @Getter
    private Long id;

    @Getter
    private Long branchId;

    @Getter
    private String name;

    @Getter
    private Integer stock;

    public Product(Long id, Long branchId, String name, Integer stock) {
        if (branchId == null) {
            throw new IllegalArgumentException("The branch ID is mandatory.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The product name cannot be empty.");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("The stock must be an integer greater than or equal to 0.");
        }
        this.id = id;
        this.branchId = branchId;
        this.name = name.trim();
        this.stock = stock;
    }

    public Product(Long branchId, String name, Integer stock) {
        this(null, branchId, name, stock);
    }

    public void updateStock(Integer newStock) {
        if (newStock == null || newStock < 0) {
            throw new IllegalArgumentException("The new stock cannot be negative.");
        }
        this.stock = newStock;
    }

    public void updateName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("The new name cannot be empty.");
        }
        this.name = newName.trim();
    }
}
