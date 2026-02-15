package com.trevora.ecommerce.order.exception;

import com.trevora.ecommerce.common.exception.BusinessException;
import com.trevora.ecommerce.common.exception.ErrorCode;

public class AddressNotFoundException extends BusinessException {
    public AddressNotFoundException(){
        super(ErrorCode.ADDRESS_NOT_FOUND);
    }
}
