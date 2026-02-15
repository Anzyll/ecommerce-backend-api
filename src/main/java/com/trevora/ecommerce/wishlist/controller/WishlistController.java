package com.trevora.ecommerce.wishlist.controller;

import com.trevora.ecommerce.wishlist.dto.AddToWishlistRequestDto;
import com.trevora.ecommerce.wishlist.dto.WishlistItemResponseDto;
import com.trevora.ecommerce.wishlist.dto.WishlistResponseDto;
import com.trevora.ecommerce.wishlist.orchestrator.WishlistOrchestrator;
import com.trevora.ecommerce.security.CustomUserDetails;
import com.trevora.ecommerce.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist", description = "Add and remove wishlist items")
public class WishlistController {
    private final WishlistOrchestrator wishlistOrchestrator;
    private final WishlistService wishlistService;
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public WishlistResponseDto addToWishlist(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody AddToWishlistRequestDto request){
        return wishlistOrchestrator.addToWishlist(user.getUser().getUserId(),request);
    }
    @DeleteMapping("/item/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public WishlistResponseDto removeFromWishlist(@AuthenticationPrincipal CustomUserDetails user,@PathVariable Long productId){
        return wishlistOrchestrator.removeFromWishlist(user.getUserId(),productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WishlistItemResponseDto> viewWishlist(@AuthenticationPrincipal CustomUserDetails user){
        return wishlistService.viewWishlist(user.getUser().getUserId());
    }
}
