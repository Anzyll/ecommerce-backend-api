package com.trevora.ecommerce.admin.user.dto;

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
