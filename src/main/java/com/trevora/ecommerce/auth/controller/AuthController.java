package com.trevora.ecommerce.auth.controller;

import com.trevora.ecommerce.auth.dto.LoginRequestDto;
import com.trevora.ecommerce.auth.dto.LoginResponseDto;
import com.trevora.ecommerce.auth.dto.RegisterRequestDto;
import com.trevora.ecommerce.auth.dto.RegisterResponseDto;
import com.trevora.ecommerce.auth.orchestrator.LoginOrchestrator;
import com.trevora.ecommerce.auth.orchestrator.RegisterOrchestrator;
import com.trevora.ecommerce.auth.service.AuthService;
import com.trevora.ecommerce.auth.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {
    private final RegisterOrchestrator registerOrchestrator;
    private final LoginOrchestrator loginOrchestrator;
    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponseDto register(@Valid @RequestBody  RegisterRequestDto request){
       return registerOrchestrator.register(request);
    }

    @PostMapping("/login")
    public LoginResponseDto  login(@Valid @RequestBody LoginRequestDto request, HttpServletResponse response){
       return loginOrchestrator.verify(request,response);
    }

    @PostMapping("/refresh")
    public LoginResponseDto refresh(
            @CookieValue("refreshToken") String token) {
        return authService.refresh(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(value = "refreshToken", required = false) String token,
            HttpServletResponse response
    ) {
        if (token != null) {
            refreshTokenService.invalidate(token);
        }

        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .path("/api/auth")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().build();
    }


}
