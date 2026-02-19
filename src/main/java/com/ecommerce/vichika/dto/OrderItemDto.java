package com.ecommerce.vichika.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        OrderProductDto product,
        int quantity,
        BigDecimal totalPrice
) {
}
