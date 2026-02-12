package com.trevora.ecommerce.orchestrator;

import com.trevora.ecommerce.dto.LoginRequestDto;
import com.trevora.ecommerce.dto.LoginResponseDto;
import com.trevora.ecommerce.service.AuthService;
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
