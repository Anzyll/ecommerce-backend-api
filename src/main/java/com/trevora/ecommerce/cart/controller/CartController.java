package com.trevora.ecommerce.cart.controller;

import com.trevora.ecommerce.cart.dto.AddToCartRequestDto;
import com.trevora.ecommerce.cart.dto.CartItemResponseDto;
import com.trevora.ecommerce.cart.dto.CartResponseDto;
import com.trevora.ecommerce.cart.dto.UpdateCartRequestDto;
import com.trevora.ecommerce.cart.entity.CartItem;
import com.trevora.ecommerce.cart.orchestrator.CartOrchestrator;
import com.trevora.ecommerce.cart.service.CartService;
import com.trevora.ecommerce.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart", description = "Add and Remove Cart Items")
public class CartController {
    private final CartOrchestrator cartOrchestrator;
    private final CartService cartService;
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemResponseDto> viewCart(@AuthenticationPrincipal CustomUserDetails user){
        return cartService.viewCart(user.getUser().getUserId());
    }

    @PatchMapping("/items/{itemId}")
    public CartResponseDto updateCartItemQuantity(@AuthenticationPrincipal CustomUserDetails user,@PathVariable Long itemId,@RequestBody UpdateCartRequestDto request){
        return  cartOrchestrator.updateCartItemQuantity(user.getUser().getUserId(),itemId,request.delta());
    }
}
