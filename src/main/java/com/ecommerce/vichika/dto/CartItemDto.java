package com.ecommerce.vichika.dto;

import java.math.BigDecimal;

public record CartItemDto(
        CartProductDto product,
        int quantity,
        BigDecimal totalPrice
) {
}