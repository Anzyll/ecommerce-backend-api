package com.trevora.ecommerce.admin.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class ProfileNotFoundException extends BusinessException {
    public ProfileNotFoundException(){
        super(ErrorCode.PROFILE_NOT_FOUND);
    }
}
