package com.ecommerce.vichika.payments;

import com.ecommerce.vichika.entities.Order;
import com.ecommerce.vichika.entities.OrderItem;
import com.ecommerce.vichika.entities.OrderStatus;
import com.ecommerce.vichika.service.CheckoutSession;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripPaymentGateway implements PaymentGateway{
    @Value("${websiteUrl}")
    private String websiteUrl;
    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    @Override
    public CheckoutSession checkoutSession(Order order) {
        try{
            // Checkout Session
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .setPaymentIntentData(createPaymentIntent(order));

            order.getItems().forEach(item -> {
                var lineItem = createLineItem(item);
                builder.addLineItem(lineItem);
            });
            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());
        }catch (StripeException ex){
            ex.getMessage();
            throw new PaymentException();
        }
    }

    private SessionCreateParams.PaymentIntentData createPaymentIntent(Order order) {
        return SessionCreateParams.PaymentIntentData.builder()
                .putMetadata("order_id", order.getId().toString())
                .build();
    }

    @Override
    public Optional<PaymentResult> parseWebhookPayload(WebhookRequest request) {
        try {
            var payload = request.getPayload();
            var signature = request.getHeaders().get("stripe-signature");
            var event = Webhook.constructEvent(payload,signature,webhookSecretKey);
            System.out.println(event.getType());
            switch (event.getType()){
                case "payment_intent.succeeded" ->
                        Optional.of(new PaymentResult(extractOrderId(event), OrderStatus.PAID));

                case "payment_intent.payment_failed" ->
                        Optional.of(new PaymentResult(extractOrderId(event), OrderStatus.FAILED));

                default -> Optional.empty();
            }
        } catch (SignatureVerificationException e) {
            throw new PaymentException("Signature verification failed");
        }
        return Optional.empty();
    }

    private Long extractOrderId(Event event) {
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
                () -> new PaymentException("Couldn't deserialize Stripe event. Check the SDK and API version.")
        );
        var paymentIntent = (PaymentIntent) stripeObject;
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(
                        createPriceData(item)
                ).build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(item.getUnitPrice()
                        .multiply(BigDecimal.valueOf(100)))
                .setProductData(
                        createProductData(item)
                )
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}
