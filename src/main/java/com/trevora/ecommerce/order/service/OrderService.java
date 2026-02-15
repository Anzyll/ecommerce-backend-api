package com.trevora.ecommerce.order.service;

import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.entity.CartItem;
import com.trevora.ecommerce.cart.exception.CartItemNotFoundException;
import com.trevora.ecommerce.cart.repository.CartRepository;
import com.trevora.ecommerce.cart.service.CartService;
import com.trevora.ecommerce.common.entity.Address;
import com.trevora.ecommerce.common.enums.CartStatus;
import com.trevora.ecommerce.common.enums.OrderStatus;
import com.trevora.ecommerce.order.entity.Order;
import com.trevora.ecommerce.order.entity.OrderItem;
import com.trevora.ecommerce.order.exception.AddressNotFoundException;
import com.trevora.ecommerce.common.repository.AddressRepository;
import com.trevora.ecommerce.order.dto.OrderResponseDto;
import com.trevora.ecommerce.common.exception.InsufficientStockException;
import com.trevora.ecommerce.order.repository.OrderItemRepository;
import com.trevora.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private  final AddressRepository addressRepository;
    private final CartService cartService;

    @Transactional
    public OrderResponseDto checkout(Long userId, Long addressId) {
        Cart cart = cartRepository.findByUser_UserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(CartItemNotFoundException::new);
        if (cart.getCartItem().isEmpty()) {
            throw new CartItemNotFoundException();
        }
        Address address = addressRepository.findByAddressIdAndUser_UserId(addressId,userId)
                .orElseThrow(AddressNotFoundException::new);
        for(CartItem item : cart.getCartItem()){
            if(item.getQuantity()>item.getProduct().getStock()){
                throw new  InsufficientStockException();
            }
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setShippingAddress(address.getAddress());
        order.setStatus(OrderStatus.CREATED);

        BigDecimal total = BigDecimal.ZERO;

        for(CartItem item : cart.getCartItem()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            order.getItems().add(orderItem);

            total = total.add(
                    item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
            item.getProduct().reduceStock(item.getQuantity());
        }
        order.setTotalAmount(total);
        orderRepository.save(order);
        cart.setStatus(CartStatus.CHECKOUT);

        return new OrderResponseDto(
                order.getOrderId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getItems(),
                order.getShippingAddress(),
                order.getCreatedAt()
        );

    }
}