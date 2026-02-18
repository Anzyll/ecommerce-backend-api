package com.trevora.ecommerce.admin.product.dto;


import java.math.BigDecimal;

public record AdminAddProductRequestDto (
        String name,
        BigDecimal price,
        int stock,
        String imageUrl,
        String description,
        Long categoryId,
        Long activityId
){
}
