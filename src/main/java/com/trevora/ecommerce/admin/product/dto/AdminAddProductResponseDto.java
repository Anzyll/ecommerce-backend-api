package com.trevora.ecommerce.admin.product.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record AdminAddProductResponseDto(
        Long productId,
        String name,
        BigDecimal price,
        int stock,
        String imageUrl,
        String description,
        String category,
        String activity,
        Instant createdAt
) {
}
