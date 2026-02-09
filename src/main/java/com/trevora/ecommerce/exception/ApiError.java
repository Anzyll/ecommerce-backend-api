package com.trevora.ecommerce.exception;

import java.time.Instant;

public record ApiError(
        String code,
        String message,
        int status,
        Instant timeStamp
) {
}
