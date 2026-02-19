package com.ecommerce.vichika.payments;

public record CheckoutResponse(
    Long orderId,
    String checkoutUrl
) {
}
