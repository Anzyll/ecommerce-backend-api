package com.trevora.ecommerce.admin.dto;

import com.trevora.ecommerce.common.entity.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record AdminOrderResponseDto(
        Long orderId,
        String email,
        List<AdminOrderItemResponseDto> items,
        String status,
        BigDecimal totalAmount,
        Instant createdAt,
        String shippingAddress
) {
}
