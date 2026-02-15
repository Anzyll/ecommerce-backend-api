package com.trevora.ecommerce.order.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDto(
        Long orderId,
        String status,
        BigDecimal totalAmount,
        List<OrderItemResponseDto> items,
        String shippingAddress,
        java.time.Instant createdAt
) {
}
