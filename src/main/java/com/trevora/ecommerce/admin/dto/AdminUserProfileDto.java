package com.trevora.ecommerce.admin.dto;

public record AdminUserProfileDto(
        Long profileId,
        String fullName,
        String email,
        java.time.Instant createdAt
) {
}
