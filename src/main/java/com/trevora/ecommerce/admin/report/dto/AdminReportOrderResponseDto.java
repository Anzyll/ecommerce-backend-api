package com.trevora.ecommerce.admin.report.dto;

import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record AdminReportOrderResponseDto (
    Long orderId,
    List<AdminReportOrderItemResponseDto> items,
    String status,
    BigDecimal totalAmount,
    String shippingAddress,
    Instant createdAt
)
{}
