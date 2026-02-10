package com.trevora.ecommerce.controller;

import com.trevora.ecommerce.dto.LoginRequestDto;
import com.trevora.ecommerce.dto.LoginResponseDto;
import com.trevora.ecommerce.dto.RegisterRequestDto;
import com.trevora.ecommerce.dto.RegisterResponseDto;
import com.trevora.ecommerce.orchestrator.LoginOrchestrator;
import com.trevora.ecommerce.orchestrator.RegisterOrchestrator;
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
       return loginOrchestrator.login(request);
    }
}
