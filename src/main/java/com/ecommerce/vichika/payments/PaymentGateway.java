package com.ecommerce.vichika.payments;

import com.ecommerce.vichika.entities.Order;
import com.ecommerce.vichika.service.CheckoutSession;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession checkoutSession(Order order);
    Optional<PaymentResult> parseWebhookPayload(WebhookRequest request);
}
