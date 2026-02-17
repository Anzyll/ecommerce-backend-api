package com.trevora.ecommerce.admin.dto;

import com.trevora.ecommerce.common.entity.User;

import java.math.BigDecimal;
import java.time.Instant;

public record AdminOrderResponseDto(
        Long orderId,
        User user,
        String status,
        BigDecimal totalAmount,
        Instant createdAt,
        String shippingAddress
) {
}
