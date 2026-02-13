package com.trevora.ecommerce.controller;

import com.trevora.ecommerce.dto.AddToWishlistRequestDto;
import com.trevora.ecommerce.dto.WishlistResponseDto;
import com.trevora.ecommerce.orchestrator.WishlistOrchestrator;
import com.trevora.ecommerce.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistOrchestrator wishlistOrchestrator;
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
}
