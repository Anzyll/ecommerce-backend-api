package com.trevora.ecommerce.cart.dto;

import java.math.BigDecimal;

public record CartItemResponseDto(
        Long id,
        String productName,
        int quantity,
        BigDecimal price,
        String imageUrl
) {
}
