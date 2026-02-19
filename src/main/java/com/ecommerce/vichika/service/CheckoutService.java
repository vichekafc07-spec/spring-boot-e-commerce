package com.ecommerce.vichika.service;

import com.ecommerce.vichika.payments.CheckoutRequest;
import com.ecommerce.vichika.payments.CheckoutResponse;
import com.ecommerce.vichika.entities.Order;
import com.ecommerce.vichika.entities.OrderStatus;
import com.ecommerce.vichika.exceptions.BadRequestExceptions;
import com.ecommerce.vichika.payments.PaymentException;
import com.ecommerce.vichika.payments.PaymentGateway;
import com.ecommerce.vichika.payments.WebhookRequest;
import com.ecommerce.vichika.repository.CartRepository;
import com.ecommerce.vichika.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest checkoutRequest){
        var cart = cartRepository.getCartWithItems(checkoutRequest.cartId())
                .orElseThrow(() -> new BadRequestExceptions("Cart not found"));
        if (cart.isEmpty()) {
            throw new BadRequestExceptions("Cart items is empty");
        }

        var order = Order.fromCart(cart,authService.getCurrentUser());
        orderRepository.save(order);

        try{
            var session = paymentGateway.checkoutSession(order);
            cartService.clearCart(cart.getId());
            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        }catch (PaymentException ex){
            orderRepository.delete(order);
            throw ex;
        }
    }

    public void handleWebhookEvent(WebhookRequest request){
        paymentGateway.parseWebhookPayload(request)
                .ifPresent(paymentResult -> {
                    var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setStatus(OrderStatus.PAID);
                    orderRepository.save(order);
                });

    }

}