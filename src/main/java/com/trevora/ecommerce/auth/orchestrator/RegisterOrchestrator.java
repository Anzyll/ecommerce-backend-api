package com.trevora.ecommerce.auth.orchestrator;

import com.trevora.ecommerce.auth.dto.RegisterRequestDto;
import com.trevora.ecommerce.auth.dto.RegisterResponseDto;
import com.trevora.ecommerce.common.entity.Profile;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterOrchestrator {
    private final UserService userService;
    public RegisterResponseDto register(RegisterRequestDto request) {
        User user = new User();
        user.setEmail(request.email());
        Profile profile = new Profile();
        profile.setFullName(request.fullName());
        profile.setUser(user);
        user.setProfile(profile);

        User savedUser = userService.register(user,request.password());
        log.info("user registered successfully: userId={}",user.getUserId());
        return new RegisterResponseDto(
                savedUser.getProfile().getCreatedAt(),
                savedUser.getUserId(),
                savedUser.getEmail(),
                savedUser.getProfile().getFullName()

        );

    }
}