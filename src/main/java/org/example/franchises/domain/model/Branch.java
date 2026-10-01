package org.example.franchises.domain.model;

import lombok.Getter;

public class Branch {

    @Getter
    private Long id;

    @Getter
    private Long FranchiseId;

    @Getter
    private String name;

    public Branch(Long id, Long franchiseId, String name) {
        if (franchiseId == null) {
            throw new IllegalArgumentException("FranchisesId cannot be null");
        }
        this.id = id;
        this.FranchiseId = franchiseId;
        this.name = name.trim();
    }

    public Branch(Long franchiseId, String name) {
        this(null, franchiseId, name);
    }

    public void updateName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("New name cannot be null or blank");
        }
        this.name = newName.trim();
    }
}
