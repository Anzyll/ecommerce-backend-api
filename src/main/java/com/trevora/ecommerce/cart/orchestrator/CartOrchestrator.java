package com.trevora.ecommerce.cart.orchestrator;

import com.trevora.ecommerce.cart.dto.AddToCartRequestDto;
import com.trevora.ecommerce.cart.dto.CartItemResponseDto;
import com.trevora.ecommerce.cart.dto.CartResponseDto;
import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
                                item.getQuantity(),
                                item.getProduct().getPrice(),
                                item.getProduct().getImage()
                        ))
                        .toList();
        return new CartResponseDto(
                cart.getCartId(),
                items,
                calculateTotal(cart)

        );
    }

    private BigDecimal calculateTotal(Cart cart) {
        return cart.getCartItem().stream()
                .map(item ->
                        item.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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
                                item.getQuantity(),
                                item.getProduct().getPrice(),
                                item.getProduct().getImage()
                        ))
                        .toList();

        return  new CartResponseDto(
                cart.getCartId(),
                items,
                calculateTotal(cart)
        );
    }

    public CartResponseDto updateCartItemQuantity(Long userId, Long itemId,  int delta) {
      Cart cart =  cartService.updateCartItemQuantity(userId,itemId,delta);
      log.info("cart item quantity updated");
        return new CartResponseDto(
                cart.getCartId(),
                cart.getCartItem().stream()
                        .map(item -> new CartItemResponseDto(
                                item.getId(),
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.getProduct().getPrice(),
                                item.getProduct().getImage()
                        ))
                        .toList(),
                calculateTotal(cart)
        );

    }

    public CartResponseDto viewCart(Long userId) {
           Cart cart = cartService.getActiveCart(userId);
              return new CartResponseDto(
                      cart.getCartId(),
                      cart.getCartItem().stream()
                              .map(item->new CartItemResponseDto(
                                      item.getId(),
                                      item.getProduct().getName(),
                                      item.getQuantity(),
                                      item.getProduct().getPrice(),
                                      item.getProduct().getImage()
                              ))
                              .toList(),
                      calculateTotal(cart)
              );
    }
}
