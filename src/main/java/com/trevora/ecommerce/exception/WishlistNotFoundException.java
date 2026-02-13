package com.trevora.ecommerce.exception;

public class WishlistNotFoundException extends BusinessException{
    public WishlistNotFoundException(){
        super(ErrorCode.WISHLIST_NOT_FOUND);
    }
}
