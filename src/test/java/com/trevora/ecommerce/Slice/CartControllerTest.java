package com.trevora.ecommerce.Slice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trevora.ecommerce.cart.controller.CartController;
import com.trevora.ecommerce.cart.dto.AddToCartRequestDto;
import com.trevora.ecommerce.cart.dto.CartResponseDto;
import com.trevora.ecommerce.cart.orchestrator.CartOrchestrator;
import com.trevora.ecommerce.common.entity.Role;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.security.CustomUserDetails;
import com.trevora.ecommerce.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(CartController.class)
@AutoConfigureMockMvc
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CartOrchestrator cartOrchestrator;
    @MockitoBean
    private JwtUtil jwtUtil;
    @Test
    @WithMockUser
    void addToCart_success_returns201()throws Exception{
        AddToCartRequestDto request = new AddToCartRequestDto(1L,2);
        CartResponseDto response  = new CartResponseDto(1L,new ArrayList<>(), BigDecimal.valueOf(1000));
        Role role = new Role();
        role.setRoleId(1L);
        role.setTitle("ROLE_USER");
        User domainUser = new User();
        domainUser.setUserId(1L);
        domainUser.setEmail("test@gmail.com");
        domainUser.setRole(role);
        CustomUserDetails principal =
                new CustomUserDetails(domainUser);

        when(cartOrchestrator.addToCart(2L,request))
                .thenReturn(response);
        mockMvc.perform(post("/api/cart/items")
                .with(user(principal))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void addToCart_withoutAuth_returns401() throws Exception {

        AddToCartRequestDto request =
                new AddToCartRequestDto(1L, 2);

        mockMvc.perform(post("/api/cart/items")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
