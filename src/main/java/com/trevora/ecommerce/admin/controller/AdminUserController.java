package com.trevora.ecommerce.admin.controller;

import com.trevora.ecommerce.admin.dto.AdminUserProfileDto;
import com.trevora.ecommerce.admin.dto.AdminUserResponseDto;
import com.trevora.ecommerce.admin.orchestrator.AdminUserOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserOrchestrator adminUserOrchestrator;
    @GetMapping
    public List<AdminUserResponseDto> getAllUsers( Pageable pageable){
        return adminUserOrchestrator.getAllUsers(pageable);
    }

    @GetMapping("/{userId}")
    public AdminUserProfileDto getProfileByUser(@PathVariable Long userId){
        return adminUserOrchestrator.getProfileByUser(userId);
    }
}
