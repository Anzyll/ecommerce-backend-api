package com.trevora.ecommerce.common.exception;

public class PasswordMismatchException extends BusinessException{
    public PasswordMismatchException(){
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
