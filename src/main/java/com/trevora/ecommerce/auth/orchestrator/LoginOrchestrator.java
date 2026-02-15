package com.trevora.ecommerce.auth.orchestrator;

import com.trevora.ecommerce.auth.dto.LoginRequestDto;
import com.trevora.ecommerce.auth.dto.LoginResponseDto;
import com.trevora.ecommerce.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginOrchestrator {

    private final AuthService authService;

    public LoginResponseDto verify(LoginRequestDto request) {
        return authService.verify(
                request.email(),
                request.password()
        );

    }
}
