package com.trevora.ecommerce.admin.product.dto;
import java.math.BigDecimal;

public record AdminUpdateProductRequestDto (
        String name,
        BigDecimal price,
        Integer stock,
        String imageUrl,
        String description,
        Long categoryId,
        Long activityId
){
}