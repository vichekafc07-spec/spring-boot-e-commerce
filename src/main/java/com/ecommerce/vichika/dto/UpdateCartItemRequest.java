package com.ecommerce.vichika.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartItemRequest(
        @NotNull(message = "Quantity must be provided.")
        @Min(value = 1,message = "Quantity must be greater than zero.")
        @Max(value = 1000, message = "Quantity must be less than or equal to 100")
        int quantity
) {
}
