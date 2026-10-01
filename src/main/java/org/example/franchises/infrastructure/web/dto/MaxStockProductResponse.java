package org.example.franchises.infrastructure.web.dto;

public record MaxStockProductResponse(
        Long branchId,
        String branchName,
        Long productId,
        String productName,
        Integer stock
) {
}
