package com.trevora.ecommerce.admin.orchestrator;

import com.trevora.ecommerce.admin.dto.AdminUserProfileDto;
import com.trevora.ecommerce.admin.dto.AdminUserResponseDto;
import com.trevora.ecommerce.admin.service.AdminUserService;
import com.trevora.ecommerce.common.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminUserOrchestrator {
    private final AdminUserService adminUserService;
    public List<AdminUserResponseDto> getAllUsers(Pageable pageable) {
       return adminUserService.getAllUsers(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort())
               .stream()
               .map(user->new AdminUserResponseDto(
                       user.getUserId(),
                       user.getEmail()
               ))
               .toList();
    }

    public AdminUserProfileDto getProfileByUser(Long userId) {
       Profile profile = adminUserService.getProfileByUser(userId);
       return  new AdminUserProfileDto(
               profile.getProfileId(),
               profile.getFullName(),
               profile.getUser().getEmail(),
               profile.getCreatedAt()
       );

    }
}
