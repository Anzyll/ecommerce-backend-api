package com.trevora.ecommerce.product.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(){
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }
}