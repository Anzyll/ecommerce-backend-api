package com.trevora.ecommerce.order.dto;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Long productId,
        String productName,
        BigDecimal price,
        int quantity

) {

}

