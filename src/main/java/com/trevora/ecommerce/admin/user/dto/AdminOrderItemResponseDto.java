package com.trevora.ecommerce.admin.user.dto;

import java.math.BigDecimal;

public record AdminOrderItemResponseDto (
        Long orderItemId,
        Long orderId,
        String productName,
        int quantity,
        BigDecimal price
){}
