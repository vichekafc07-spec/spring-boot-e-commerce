package com.ecommerce.vichika.payments;

import com.ecommerce.vichika.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private OrderStatus paymentStatus;
}
