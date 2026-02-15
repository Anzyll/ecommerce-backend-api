package com.trevora.ecommerce.order.orchestrator;

import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.entity.CartItem;
import com.trevora.ecommerce.cart.exception.CartItemNotFoundException;
import com.trevora.ecommerce.cart.repository.CartRepository;
import com.trevora.ecommerce.common.enums.CartStatus;
import com.trevora.ecommerce.common.exception.InsufficientStockException;
import com.trevora.ecommerce.order.dto.OrderItemResponseDto;
import com.trevora.ecommerce.order.dto.OrderResponseDto;
import com.trevora.ecommerce.order.entity.Order;
import com.trevora.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderOrchestrator {
    private final OrderService orderService;
    private final CartRepository cartRepository;
    @Transactional
    public OrderResponseDto checkout(Long userId, String shippingAddress) {
        Cart cart = cartRepository.findByUser_UserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(CartItemNotFoundException::new);
        if (cart.getCartItem().isEmpty()) {
            throw new CartItemNotFoundException();
        }
        for(CartItem item : cart.getCartItem()){
            if(item.getQuantity()>item.getProduct().getStock()){
                throw new InsufficientStockException();
            }
        }
        Order order = orderService.createOrder(cart,shippingAddress);
        cart.setStatus(CartStatus.CHECKOUT);
        cartRepository.save(cart);

        List<OrderItemResponseDto> items =
                order.getItems().stream()
                        .map(item -> new OrderItemResponseDto(
                                item.getProduct().getProductId(),
                                item.getProduct().getName(),
                                item.getPrice(),
                                item.getQuantity()
                        ))
                        .toList();

        return new OrderResponseDto(
                order.getOrderId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                items,
                order.getShippingAddress(),
                order.getCreatedAt()
        );
    }

}
