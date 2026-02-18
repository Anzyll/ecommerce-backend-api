package com.trevora.ecommerce.admin.product.dto;

import java.math.BigDecimal;

public record AdminProductResponseDto(
    Long productId,
    String productName,
    BigDecimal price,
    int stock,
    String imageUrl,
    String category,
    String activity
) {
}
