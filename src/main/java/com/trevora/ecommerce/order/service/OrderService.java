package com.trevora.ecommerce.order.service;

import com.trevora.ecommerce.cart.entity.Cart;
import com.trevora.ecommerce.cart.entity.CartItem;
import com.trevora.ecommerce.common.enums.OrderStatus;
import com.trevora.ecommerce.order.dto.OrderItemResponseDto;
import com.trevora.ecommerce.order.dto.OrderResponseDto;
import com.trevora.ecommerce.order.entity.Order;
import com.trevora.ecommerce.order.entity.OrderItem;
import com.trevora.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
 @Transactional
    public Order createOrder(Cart cart, String shippingAddress) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setShippingAddress(shippingAddress);
        order.setStatus(OrderStatus.CREATED);
        BigDecimal total = BigDecimal.ZERO;

        for(CartItem item : cart.getCartItem()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setPrice(item.getProduct().getPrice());
            orderItem.setQuantity(item.getQuantity());
            order.getItems().add(orderItem);
            total = total.add(
                    item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
            item.getProduct().reduceStock(item.getQuantity());
        }
        order.setTotalAmount(total);
       return orderRepository.save(order);
    }
    public void markPaid(Order order) {
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Order not payable");
        }
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrderHistory(Long userId) {
      return orderRepository.findAllByUser_UserId_OrderByCreatedAtDesc(userId);

    }
}