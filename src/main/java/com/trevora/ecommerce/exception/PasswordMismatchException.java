package com.trevora.ecommerce.exception;

public class PasswordMismatchException extends BusinessException{
    public PasswordMismatchException(){
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
