package com.trevora.ecommerce.order.orchestrator;

import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.entity.CartItem;
import com.trevora.ecommerce.cart.exception.CartItemNotFoundException;
import com.trevora.ecommerce.cart.exception.CartNotFoundException;
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
                .orElseThrow(CartNotFoundException::new);
        if (cart.getCartItem().isEmpty()) {
            throw new CartItemNotFoundException();
        }
        for(CartItem item : cart.getCartItem()){
            if(item.getQuantity()>item.getProduct().getStock()){
                throw new InsufficientStockException();
            }
        }
        Order order = orderService.createOrder(cart,shippingAddress);
        orderService.markPaid(order);
        cart.setStatus(CartStatus.CHECKOUT);
        cartRepository.save(cart);
        return mapToOrderResponse(order);
    }

    public List<OrderResponseDto> getOrderHistory(Long userId) {
        return orderService.getOrderHistory(userId)
                .stream()
                .map(this::mapToOrderResponse)
                .toList();

    }
    private List<OrderItemResponseDto> mapOrderItems(Order order) {
        return order.getItems()
                .stream()
                .map(item -> new OrderItemResponseDto(
                        item.getProduct().getProductId(),
                        item.getProduct().getName(),
                        item.getPrice(),
                        item.getQuantity()
                ))
                .toList();
    }
    private OrderResponseDto mapToOrderResponse(Order order) {
        return new OrderResponseDto(
                order.getOrderId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                mapOrderItems(order),
                order.getShippingAddress(),
                order.getCreatedAt()
        );
    }

}
