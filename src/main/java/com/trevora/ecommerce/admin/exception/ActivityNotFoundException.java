package com.trevora.ecommerce.admin.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class ActivityNotFoundException extends BusinessException {
    public ActivityNotFoundException(){
        super(ErrorCode.ACTIVITY_NOT_FOUND);
    }
}
