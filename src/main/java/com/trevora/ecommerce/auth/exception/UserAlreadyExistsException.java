package com.trevora.ecommerce.auth.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException(){
        super(ErrorCode.USER_ALREADY_EXISTS);
    }
}
