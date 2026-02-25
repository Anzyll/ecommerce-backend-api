package com.trevora.ecommerce.controller;
import com.trevora.ecommerce.auth.controller.AuthController;
import com.trevora.ecommerce.auth.dto.LoginRequestDto;
import com.trevora.ecommerce.auth.dto.LoginResponseDto;
import com.trevora.ecommerce.auth.orchestrator.LoginOrchestrator;
import com.trevora.ecommerce.auth.orchestrator.RegisterOrchestrator;
import com.trevora.ecommerce.auth.service.AuthService;
import com.trevora.ecommerce.auth.service.RefreshTokenService;
import com.trevora.ecommerce.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private JwtUtil jwtUtil;
    @MockitoBean
    private RegisterOrchestrator registerOrchestrator;
    @MockitoBean
    private LoginOrchestrator loginOrchestrator;
    @MockitoBean
    private RefreshTokenService refreshTokenService;
    @MockitoBean
    private AuthService authService;
        @Test
        @WithMockUser
        void login_success_returns200AndToken() throws Exception {

            LoginRequestDto request =
                    new LoginRequestDto("user@gmail.com", "password123");

            LoginResponseDto response =
                    new LoginResponseDto("access-token");

            when(loginOrchestrator.verify(any(), any()))
                    .thenReturn(response);

            mockMvc.perform(post("/api/auth/login")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").value("access-token"));
        }

        @Test
        @WithMockUser
        void  login_failure_returns401() throws Exception {
            LoginRequestDto request =
                    new LoginRequestDto("user@gmail.com", "password123");
            when(loginOrchestrator.verify(any(),any()))
                    .thenThrow(new BadCredentialsException("invalid credentials"));
            mockMvc.perform(post("/api/auth/login")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                      .andExpect(status().isUnauthorized());

        }

        @Test
        void cart_withoutAuth_returns403()throws Exception{
            mockMvc.perform(post("/api/auth/login"))
                    .andExpect(status().isForbidden());

        }

}
