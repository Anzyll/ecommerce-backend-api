package com.trevora.ecommerce.common.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_ALREADY_EXISTS("user already exists with this email", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND("role not exist for user",HttpStatus.NOT_FOUND),
    VALIDATION_ERROR("validation failed",HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS("invalid email or password",HttpStatus.UNAUTHORIZED),
    PRODUCT_NOT_FOUND("product not found",HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("user not found",HttpStatus.NOT_FOUND),
    CART_NOT_FOUND("cart not found",HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND("item not found",HttpStatus.NOT_FOUND),
    WISHLIST_NOT_FOUND("wishlist not found",HttpStatus.NOT_FOUND),
    WISHLIST_ITEM_NOT_FOUND("item not found",HttpStatus.NOT_FOUND),
    PASSWORD_MISMATCH("password does not match",HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_FOUND("no address found for the user",HttpStatus.NOT_FOUND),
    INSUFICIENT_STOCK("insuficient stock for the product",HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN("refresh token is invalid",HttpStatus.BAD_REQUEST),
    EXPIRED_REFRESH_TOKEN("refresh token is expired",HttpStatus.BAD_REQUEST),
    PROFILE_NOT_FOUND("user profile not found",HttpStatus.NOT_FOUND);


    private final String message;
    private final HttpStatus status;
    ErrorCode(String message,HttpStatus status){
        this.message=message;
        this.status=status;
    }
}
