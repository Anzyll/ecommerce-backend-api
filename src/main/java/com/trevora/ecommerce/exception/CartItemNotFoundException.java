package com.trevora.ecommerce.exception;

public class CartItemNotFoundException  extends  BusinessException{
        public CartItemNotFoundException(){
            super(ErrorCode.CART_ITEM_NOT_FOUND);
        }

}
