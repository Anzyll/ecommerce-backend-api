package com.trevora.ecommerce.wishlist.dto;


import java.util.List;

public record WishlistResponseDto(
        Long wishlistId,
        List<WishlistItemResponseDto> items
) {

}
