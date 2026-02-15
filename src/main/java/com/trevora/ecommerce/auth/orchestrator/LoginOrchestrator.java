package com.trevora.ecommerce.auth.orchestrator;

import com.trevora.ecommerce.auth.dto.LoginRequestDto;
import com.trevora.ecommerce.auth.dto.LoginResponseDto;
import com.trevora.ecommerce.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginOrchestrator {

    private final AuthService authService;

    public LoginResponseDto verify(
            LoginRequestDto request,
            HttpServletResponse response
    ) {
        return authService.verify(
                request.email(),
                request.password(),
                response
        );
    }
}
