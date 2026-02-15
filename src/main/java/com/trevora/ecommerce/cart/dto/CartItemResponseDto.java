package com.trevora.ecommerce.cart.dto;

public record CartItemResponseDto(
        Long id,
        String productName,
        int quantity
) {
}
