package com.trevora.ecommerce.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_ALREADY_EXISTS("user already exists with this email", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND("role not exist for user",HttpStatus.NOT_FOUND),
    PASSWORD_MISMATCH("password and confirm password do not match",HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR("validation failed",HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS("invalid email or password",HttpStatus.UNAUTHORIZED),
    PRODUCT_NOT_FOUND("product not found",HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("user not found",HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
    ErrorCode(String message,HttpStatus status){
        this.message=message;
        this.status=status;
    }
}
