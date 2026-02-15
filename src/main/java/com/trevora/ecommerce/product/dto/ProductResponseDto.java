package com.trevora.ecommerce.product.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long productId,
        String name,
        BigDecimal price,
        int stock,
        String imageUrl,
        String category,
        String activity
) {}
