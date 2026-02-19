package com.ecommerce.vichika.dto;

import java.math.BigDecimal;

public record OrderProductDto(
        Long id,
        String name,
        BigDecimal price
) {
}
