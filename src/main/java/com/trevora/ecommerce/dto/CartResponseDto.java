package com.trevora.ecommerce.dto;

import java.util.List;

public record CartResponseDto(
        Long cartId,
        List<CartItemResponseDto> items
) {
}
