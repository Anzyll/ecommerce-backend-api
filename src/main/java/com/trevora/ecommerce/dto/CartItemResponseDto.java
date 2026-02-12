package com.trevora.ecommerce.dto;

public record CartItemResponseDto(
        Long id,
        String productName,
        int quantity
) {
}
