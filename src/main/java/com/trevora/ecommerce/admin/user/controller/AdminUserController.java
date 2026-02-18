package com.trevora.ecommerce.admin.user.controller;

import com.trevora.ecommerce.admin.user.dto.AdminOrderResponseDto;
import com.trevora.ecommerce.admin.user.dto.AdminUserProfileDto;
import com.trevora.ecommerce.admin.user.dto.AdminUserResponseDto;
import com.trevora.ecommerce.admin.user.orchestrator.AdminUserOrchestrator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "user management")
public class AdminUserController {
    private final AdminUserOrchestrator adminUserOrchestrator;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdminUserResponseDto> getAllUsers(@ParameterObject Pageable pageable){
        return adminUserOrchestrator.getAllUsers(pageable);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public AdminUserProfileDto getProfileByUser(@PathVariable Long userId){
        return adminUserOrchestrator.getProfileByUser(userId);
    }

    @GetMapping("/{userId}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<AdminOrderResponseDto> getOrdersByUser(@PathVariable Long userId){
        return adminUserOrchestrator.getOrdersByUser(userId);
    }
}
