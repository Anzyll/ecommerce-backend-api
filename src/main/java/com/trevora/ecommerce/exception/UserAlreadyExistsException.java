package com.trevora.ecommerce.exception;

public class UserAlreadyExistsException extends BusinessException{
    public UserAlreadyExistsException(){
        super(ErrorCode.USER_ALREADY_EXISTS);
    }
}
