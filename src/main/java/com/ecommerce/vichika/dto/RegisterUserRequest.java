package com.ecommerce.vichika.dto;

import com.ecommerce.vichika.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be less than 255 characters")
        String name ,
        @NotBlank(message = "Email is required")
        @Email(message = "Email must valid")
        @Lowercase(message = "Email must lowercase")
        String email ,
        @NotBlank(message = "Password is required")
        @Size(max = 25, min = 6, message = "Password must between 6 to 25 characters")
        String password) {
}
