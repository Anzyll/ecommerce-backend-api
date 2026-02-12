package com.trevora.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddToCartRequestDto(
        @NotNull
        Long productId,
        @NotNull@Min(1)
        int quantity
) {
}
