package com.trevora.ecommerce.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiError> handleBusinessException(BusinessException ex){
        ErrorCode errorCode = ex.getErrorCode();
        ApiError error = new ApiError(
                errorCode.name(),
                errorCode.getMessage(),
                errorCode.getStatus().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public  ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex){
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
      log.warn("Validation failed: {}", ex.getMessage());
        ApiError error = new ApiError(
                errorCode.name(),
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(),
                errorCode.getStatus().value(),
                Instant.now()
        );
        return ResponseEntity
                .badRequest()
                .body(error);
   }
}
