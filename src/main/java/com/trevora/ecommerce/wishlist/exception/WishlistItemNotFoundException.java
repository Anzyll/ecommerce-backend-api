package com.trevora.ecommerce.wishlist.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class WishlistItemNotFoundException extends BusinessException {
    public WishlistItemNotFoundException(){
        super(ErrorCode.WISHLIST_ITEM_NOT_FOUND);
    }
}
