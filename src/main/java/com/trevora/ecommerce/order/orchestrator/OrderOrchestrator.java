package com.trevora.ecommerce.order.orchestrator;

import com.trevora.ecommerce.order.dto.CheckoutRequestDto;
import com.trevora.ecommerce.order.dto.OrderResponseDto;
import com.trevora.ecommerce.order.entity.Order;
import com.trevora.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderOrchestrator {
    private final OrderService orderService;
//    public OrderResponseDto checkout(String email,Long addressId) {
////        Order order = orderService.checkout(email,addressId);
//    }
}
