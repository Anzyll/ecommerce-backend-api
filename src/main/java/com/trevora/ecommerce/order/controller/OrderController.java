package com.trevora.ecommerce.order.controller;

import com.trevora.ecommerce.order.dto.CheckoutRequestDto;
import com.trevora.ecommerce.order.dto.OrderResponseDto;
import com.trevora.ecommerce.order.orchestrator.OrderOrchestrator;
import com.trevora.ecommerce.order.service.OrderService;
import com.trevora.ecommerce.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private  final OrderOrchestrator orderOrchestrator;
    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto checkout(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody CheckoutRequestDto request) {
       return orderOrchestrator.checkout(user.getUser().getUserId(),request.shippingAddress());
    }
}
