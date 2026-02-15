package com.trevora.ecommerce.auth.controller;

import com.trevora.ecommerce.auth.dto.LoginRequestDto;
import com.trevora.ecommerce.auth.dto.LoginResponseDto;
import com.trevora.ecommerce.auth.dto.RegisterRequestDto;
import com.trevora.ecommerce.auth.dto.RegisterResponseDto;
import com.trevora.ecommerce.auth.orchestrator.LoginOrchestrator;
import com.trevora.ecommerce.auth.orchestrator.RegisterOrchestrator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final RegisterOrchestrator registerOrchestrator;
    private final LoginOrchestrator loginOrchestrator;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponseDto register(@Valid @RequestBody  RegisterRequestDto request){
       return registerOrchestrator.register(request);
    }

    @PostMapping("/login")
    public LoginResponseDto  login(@Valid @RequestBody LoginRequestDto request){
       return loginOrchestrator.verify(request);
    }
}
