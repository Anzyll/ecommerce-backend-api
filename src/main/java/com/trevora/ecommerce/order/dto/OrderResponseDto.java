package com.trevora.ecommerce.order.dto;

import com.trevora.ecommerce.order.entity.OrderItem;

import java.math.BigDecimal;
import java.time.Instant;
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
