package org.example.franchises.domain.model;

import lombok.Getter;

public class Branch {

    @Getter
    private Long id;

    @Getter
    private Long FranchisesId;

    @Getter
    private String name;

    public Branch(Long id, Long franchisesId, String name) {
        if (franchisesId == null) {
            throw new IllegalArgumentException("FranchisesId cannot be null");
        }
        this.id = id;
        this.FranchisesId = franchisesId;
        this.name = name.trim();
    }

    public Branch(Long franchisesId, String name) {
        this(null, franchisesId, name);
    }

    public void updateName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("New name cannot be null or blank");
        }
        this.name = newName.trim();
    }
}
