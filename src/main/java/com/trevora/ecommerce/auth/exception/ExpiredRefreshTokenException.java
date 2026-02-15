package com.trevora.ecommerce.auth.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class ExpiredRefreshTokenException extends BusinessException {
    public ExpiredRefreshTokenException(){
        super(ErrorCode.EXPIRED_REFRESH_TOKEN);
    }
}
