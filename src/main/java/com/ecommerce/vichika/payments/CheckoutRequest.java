package com.ecommerce.vichika.payments;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CheckoutRequest(
        @NotNull(message = "Cart Id is required") UUID cartId
) {
}
