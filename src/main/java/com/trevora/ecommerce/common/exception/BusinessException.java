package com.trevora.ecommerce.common.exception;

import lombok.Getter;
import com.trevora.ecommerce.common.exception.ErrorCode;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;
    protected BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }
}
