package com.trevora.ecommerce.auth.dto;
import jakarta.validation.constraints.*;
public record LoginRequestDto(
        @Email @NotBlank
        String email,
        @NotBlank @Size(min = 8)
        String password
){
}
