package com.trevora.ecommerce.common.exception;

import java.time.Instant;

public record ApiError(
        String code,
        String message,
        int status,
        Instant timeStamp
) {
}
