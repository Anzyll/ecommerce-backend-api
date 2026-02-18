package com.trevora.ecommerce.admin.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class CategoryNotFoundException extends BusinessException {
    public CategoryNotFoundException(){
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}
