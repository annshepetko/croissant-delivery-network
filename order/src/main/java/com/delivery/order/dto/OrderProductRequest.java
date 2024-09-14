package com.delivery.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderProductRequest (

        @JsonProperty(value = "id")
        Long productId,

        String name,

        Integer categoryId,

        BigDecimal price,

        Integer amount

) {
}
