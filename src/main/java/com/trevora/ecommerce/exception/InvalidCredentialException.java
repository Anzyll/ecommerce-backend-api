package com.trevora.ecommerce.exception;

public class InvalidCredentialException extends BusinessException{
    public InvalidCredentialException(){
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}
