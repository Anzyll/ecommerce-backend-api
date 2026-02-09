package com.trevora.ecommerce.orchestrator;

import com.trevora.ecommerce.dto.LoginRequestDto;
import com.trevora.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginOrchestrator {
    private final UserService userService;
    public void login(LoginRequestDto request){
        userService.login(request.email(),request.password());
    }

}
