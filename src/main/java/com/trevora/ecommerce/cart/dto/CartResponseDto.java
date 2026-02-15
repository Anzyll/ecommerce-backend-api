package com.trevora.ecommerce.cart.dto;

import java.util.List;

public record CartResponseDto(
        Long cartId,
        List<CartItemResponseDto> items
) {
}
