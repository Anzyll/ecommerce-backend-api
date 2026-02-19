package com.trevora.ecommerce.admin.product.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record AdminAddProductRequestDto (
        @NotBlank
        String name,
        @NotNull
        BigDecimal price,
        @Positive
        int stock,
        @NotBlank
        String imageUrl,
        String description,
        @NotNull
        Long categoryId,
        @NotNull
        Long activityId
){
}
