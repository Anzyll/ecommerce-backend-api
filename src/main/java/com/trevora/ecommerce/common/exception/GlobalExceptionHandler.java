package com.trevora.ecommerce.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiError> handleBusinessException(BusinessException ex){
        ErrorCode errorCode = ex.getErrorCode();
        log.warn(
                "Business exception occurred: code={}, message={}",
                errorCode.name(),
                errorCode.getMessage()
        );

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
      String validationMessage = ex.getBindingResult()
              .getFieldErrors()
              .stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.joining(", "));

      log.warn("Validation failed: {}", validationMessage);

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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;
        log.warn("Invalid credentials for login ");
        ApiError error = new ApiError(
                errorCode.name(),
                "Invalid email or password",
                errorCode.getStatus().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(error);
    }


}
