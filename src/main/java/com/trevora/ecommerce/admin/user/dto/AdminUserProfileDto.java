package com.trevora.ecommerce.admin.user.dto;

public record AdminUserProfileDto(
        Long profileId,
        String fullName,
        String email,
        java.time.Instant createdAt
) {
}
