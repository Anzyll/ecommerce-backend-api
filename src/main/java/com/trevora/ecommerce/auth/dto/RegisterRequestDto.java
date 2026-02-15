package com.trevora.ecommerce.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank
        @Pattern(regexp = "^[A-Za-z ]+$",message = "invalid name")
        String fullName,
        @Email @NotBlank
        String email,
        @NotBlank @Size(min = 8)
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%&*]).*$",message = "invalid password format")
        String password,
        @NotBlank @Size(min = 8)
        String confirmPassword

) {}
