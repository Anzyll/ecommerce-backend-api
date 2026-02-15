package com.trevora.ecommerce.auth.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class InvalidRefreshTokenException extends BusinessException {
    public InvalidRefreshTokenException(){
        super(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}
