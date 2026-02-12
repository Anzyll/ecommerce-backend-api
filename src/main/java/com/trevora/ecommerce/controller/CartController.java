package com.trevora.ecommerce.controller;

import com.trevora.ecommerce.dto.AddToCartRequestDto;
import com.trevora.ecommerce.dto.CartResponseDto;
import com.trevora.ecommerce.orchestrator.CartOrchestrator;
import com.trevora.ecommerce.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartOrchestrator cartOrchestrator;
    @PostMapping("/items")
    public CartResponseDto addToCart(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody AddToCartRequestDto request){
        return cartOrchestrator.addToCart(user.getUser().getUserId(),request);
    }
}
