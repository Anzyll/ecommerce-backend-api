package com.trevora.ecommerce.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;
    BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }
}
