package com.trevora.ecommerce.dto;

import java.time.Instant;

public record RegisterResponseDto(
        Instant createdAt,
        Long user_id,
        String fullName,
        String email
) {}
