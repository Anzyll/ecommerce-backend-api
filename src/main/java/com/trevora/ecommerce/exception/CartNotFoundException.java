package com.trevora.ecommerce.exception;

public class CartNotFoundException extends BusinessException{
    public CartNotFoundException(){
        super(ErrorCode.CART_NOT_FOUND);
    }
}
