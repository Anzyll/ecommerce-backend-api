package com.trevora.ecommerce.cart.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class CartItemNotFoundException  extends BusinessException {
        public CartItemNotFoundException(){
            super(ErrorCode.CART_ITEM_NOT_FOUND);
        }

}
