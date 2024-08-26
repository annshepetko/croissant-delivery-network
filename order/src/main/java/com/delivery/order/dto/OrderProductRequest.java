package com.delivery.order.dto;

import java.math.BigDecimal;

public record OrderProductRequest (
        Long id,
        String name,
        Integer categoryId,
        BigDecimal price,
        Integer amount
) {
}
