package com.trevora.ecommerce.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long orderId,
        String status,
        BigDecimal totalAmount,
        List<com.trevora.ecommerce.order.entity.OrderItem> items,
        String shippingAddress,
        LocalDateTime createdAt
) {
}
