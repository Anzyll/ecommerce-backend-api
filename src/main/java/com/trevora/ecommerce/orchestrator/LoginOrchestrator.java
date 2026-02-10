package com.trevora.ecommerce.orchestrator;

import com.trevora.ecommerce.dto.LoginRequestDto;
import com.trevora.ecommerce.dto.LoginResponseDto;
import com.trevora.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginOrchestrator {
    private final UserService userService;
    public LoginResponseDto login(LoginRequestDto request){
       String token =  userService.login(request.email(),request.password());
        return new LoginResponseDto(token);
    }
}
