package com.trevora.ecommerce.auth.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class InvalidCredentialException extends BusinessException {
    public InvalidCredentialException(){
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}
