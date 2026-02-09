package com.trevora.ecommerce.exception;

public class RoleNotFoundException extends BusinessException{
    public RoleNotFoundException(){
        super(ErrorCode.ROLE_NOT_FOUND);
    }
}
