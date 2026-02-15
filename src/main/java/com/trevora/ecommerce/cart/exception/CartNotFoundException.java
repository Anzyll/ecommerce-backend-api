package com.trevora.ecommerce.cart.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class CartNotFoundException extends BusinessException {
    public CartNotFoundException(){
        super(ErrorCode.CART_NOT_FOUND);
    }
}
