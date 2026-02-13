package com.trevora.ecommerce.orchestrator;

import com.trevora.ecommerce.dto.AddToWishlistRequestDto;
import com.trevora.ecommerce.dto.WishlistItemResponseDto;
import com.trevora.ecommerce.dto.WishlistResponseDto;
import com.trevora.ecommerce.entity.Wishlist;
import com.trevora.ecommerce.entity.WishlistItem;
import com.trevora.ecommerce.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WishlistOrchestrator {
    private final WishlistService wishlistService;
    public WishlistResponseDto addToWishlist(Long userId, AddToWishlistRequestDto request) {
        Wishlist wishlist = wishlistService.addToWishlist(userId,request.productId());
       List<WishlistItemResponseDto> items = wishlist.getItems()
               .stream()
               .map(item-> new WishlistItemResponseDto(
                   item.getId(),
                   item.getProduct().getName()
               ))
               .toList();
       return  new WishlistResponseDto(
               wishlist.getWishlistId(),
               items
       );
    }

    public WishlistResponseDto removeFromWishlist(Long userId, Long productId) {
        Wishlist wishlist = wishlistService.removeFromWishlist(userId,productId);
        List<WishlistItemResponseDto> items = wishlist.getItems()
                .stream().map(item->new WishlistItemResponseDto(
                        item.getId(),
                        item.getProduct().getName()
                ))
                .toList();
        return new WishlistResponseDto(
                wishlist.getWishlistId(),
                items
        );
    }
}
