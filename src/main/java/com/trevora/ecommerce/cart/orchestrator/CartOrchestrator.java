package com.trevora.ecommerce.cart.orchestrator;

import com.trevora.ecommerce.cart.dto.AddToCartRequestDto;
import com.trevora.ecommerce.cart.dto.CartItemResponseDto;
import com.trevora.ecommerce.cart.dto.CartResponseDto;
import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartOrchestrator {
    private final CartService cartService;
    public CartResponseDto addToCart(Long userId, @Valid AddToCartRequestDto request) {
        Cart cart = cartService.addToCart(
                userId,
                request.productId(),
                request.quantity()
        );
        List<CartItemResponseDto> items =
                cart.getCartItem().stream()
                        .map(item -> new CartItemResponseDto(
                                item.getProduct().getProductId(),
                                item.getProduct().getName(),
                                item.getQuantity()
                        ))
                        .toList();
        return new CartResponseDto(
                cart.getCartId(),
                items
        );
    }

    public CartResponseDto removeFromCart(Long userId, Long productId, Integer quantity) {
        Cart cart = cartService.removeFromCart(
                userId,
                productId,
                quantity
        );
        List<CartItemResponseDto> items =
                cart.getCartItem().stream()
                        .map(item->new CartItemResponseDto(
                                item.getId(),
                                item.getProduct().getName(),
                                item.getQuantity()
                        ))
                        .toList();

        return  new CartResponseDto(
                cart.getCartId(),
                items
        );
    }
}
