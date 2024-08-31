package com.delivery.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record OrderProductRequest (

        @JsonProperty(value = "id")

        Long productId,

        String name,

        Integer categoryId,

        BigDecimal price,

        Integer amount

) {
}
