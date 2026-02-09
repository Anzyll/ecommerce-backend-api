package com.trevora.ecommerce.orchestrator;

import com.trevora.ecommerce.dto.RegisterRequestDto;
import com.trevora.ecommerce.dto.RegisterResponseDto;
import com.trevora.ecommerce.entity.Profile;
import com.trevora.ecommerce.entity.User;
import com.trevora.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
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
        return new RegisterResponseDto(
                savedUser.getProfile().getCreatedAt(),
                savedUser.getUserId(),
                savedUser.getEmail(),
                savedUser.getProfile().getFullName()

        );

    }
}