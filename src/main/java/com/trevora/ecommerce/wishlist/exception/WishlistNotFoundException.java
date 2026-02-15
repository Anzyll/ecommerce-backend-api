package com.trevora.ecommerce.wishlist.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class WishlistNotFoundException extends BusinessException {
    public WishlistNotFoundException(){
        super(ErrorCode.WISHLIST_NOT_FOUND);
    }
}
