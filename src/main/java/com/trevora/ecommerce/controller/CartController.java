package com.trevora.ecommerce.controller;

import com.trevora.ecommerce.dto.AddToCartRequestDto;
import com.trevora.ecommerce.dto.CartResponseDto;
import com.trevora.ecommerce.entity.Cart;
import com.trevora.ecommerce.orchestrator.CartOrchestrator;
import com.trevora.ecommerce.security.CustomUserDetails;
import com.trevora.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartOrchestrator cartOrchestrator;
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponseDto addToCart(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody AddToCartRequestDto request){
        return cartOrchestrator.addToCart(user.getUser().getUserId(),request);
    }
    @DeleteMapping("/item/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDto removeFromCart(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long id, @RequestParam(required = false) Integer quantity){
       return cartOrchestrator.removeFromCart(user.getUser().getUserId(),id,quantity);
    }
}
