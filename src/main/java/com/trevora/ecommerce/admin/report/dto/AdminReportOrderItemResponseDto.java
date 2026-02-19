package com.trevora.ecommerce.admin.report.dto;
import java.math.BigDecimal;

public record AdminReportOrderItemResponseDto(
        Long orderItemId,
        Long orderId,
        String productName,
        int quantity,
        BigDecimal price
) {
}
