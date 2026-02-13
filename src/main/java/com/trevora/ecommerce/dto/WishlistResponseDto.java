package com.trevora.ecommerce.dto;


import java.util.List;

public record WishlistResponseDto(
        Long wishlistId,
        List<WishlistItemResponseDto> items
) {

}
