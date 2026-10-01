package org.example.franchises.domain.model;

import lombok.Getter;

public class Franchise {

    @Getter
    private Long id;

    @Getter
    private String name;

    public Franchise(Long id, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Franchise name cannot be null or blank");
        }
        this.id = id;
        this.name = name;
    }

    public Franchise(String name) {
        this(null, name);
    }

    public void updateName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Franchise new name cannot be null or blank");
        }
        this.name = newName.trim();
    }
}
