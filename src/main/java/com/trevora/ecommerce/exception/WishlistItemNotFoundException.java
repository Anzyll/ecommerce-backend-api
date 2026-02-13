package com.trevora.ecommerce.exception;

public class WishlistItemNotFoundException extends BusinessException{
    public WishlistItemNotFoundException(){
        super(ErrorCode.WISHLIST_ITEM_NOT_FOUND);
    }
}
