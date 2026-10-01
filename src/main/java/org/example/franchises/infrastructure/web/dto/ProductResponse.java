package org.example.franchises.infrastructure.web.dto;

public record ProductResponse(Long id, String name, Integer stock, Long branchId) {
}
