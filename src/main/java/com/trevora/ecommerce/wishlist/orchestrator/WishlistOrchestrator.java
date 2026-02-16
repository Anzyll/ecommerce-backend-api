package com.trevora.ecommerce.wishlist.orchestrator;

import com.trevora.ecommerce.wishlist.dto.AddToWishlistRequestDto;
import com.trevora.ecommerce.wishlist.dto.WishlistItemResponseDto;
import com.trevora.ecommerce.wishlist.dto.WishlistResponseDto;
import com.trevora.ecommerce.wishlist.entity.Wishlist;
import com.trevora.ecommerce.wishlist.service.WishlistService;
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

    public List<WishlistItemResponseDto> viewWishlist(Long userId) {
        return wishlistService.viewWishlist(userId)
                .stream()
                .map(item->new WishlistItemResponseDto(
                        item.getId(),
                        item.getProduct().getName()
                ))
                .toList();
    }
}
