package com.trevora.ecommerce.auth.dto;

public record AuthMeResponseDto(
        Long userId,
        String email,
        String role
) {}