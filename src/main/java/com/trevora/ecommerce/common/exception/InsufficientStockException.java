package com.trevora.ecommerce.common.exception;

public class InsufficientStockException extends BusinessException {
    public InsufficientStockException(){
        super(ErrorCode.INSUFFICIENT_STOCK);
    }
}
