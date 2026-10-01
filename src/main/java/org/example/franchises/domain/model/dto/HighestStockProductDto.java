package org.example.franchises.domain.model.dto;

public record HighestStockProductDto(
        Long branchId,
        String branchName,
        Long productId,
        String productName,
        Integer stock
) {
}
