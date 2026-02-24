package com.trevora.ecommerce.Slice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trevora.ecommerce.common.entity.Role;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.common.enums.OrderStatus;
import com.trevora.ecommerce.common.enums.RoleName;
import com.trevora.ecommerce.order.controller.OrderController;
import com.trevora.ecommerce.order.dto.CheckoutRequestDto;
import com.trevora.ecommerce.order.dto.OrderResponseDto;
import com.trevora.ecommerce.order.orchestrator.OrderOrchestrator;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.trevora.ecommerce.security.CustomUserDetails;
import com.trevora.ecommerce.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private JwtUtil jwtUtil;
    @MockitoBean
    private OrderOrchestrator orderOrchestrator;
    @Test
    @WithMockUser
    void checkout_success_returns201()throws Exception{
        CheckoutRequestDto request = new CheckoutRequestDto("Kerala");
        OrderResponseDto response = new OrderResponseDto(1L, OrderStatus.CREATED.name(), BigDecimal.valueOf(1000),new ArrayList<>(),"kerala", Instant.now());
        Role role = new Role();
        role.setRoleId(1L);
        role.setTitle("ROLE_USER");
        User domainUser = new User();
                domainUser.setUserId(1L);
                domainUser.setEmail("test@gmail.com");
                domainUser.setRole(role);
        CustomUserDetails principal =
                new CustomUserDetails(domainUser);

        when(orderOrchestrator.checkout(anyLong(), anyString()))
                .thenReturn(response);
        mockMvc.perform(post("/api/orders/checkout")
                        .with(user(principal))
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
              .andExpect(jsonPath("$.status").value("CREATED"));

    }
}

