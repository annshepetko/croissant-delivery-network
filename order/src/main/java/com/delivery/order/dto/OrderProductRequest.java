package com.delivery.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@Valid
public record OrderProductRequest (

        @JsonProperty(value = "id")
        Long productId,

        @Size(min = 3, max = 65, message = "Name is too small")
        String name,

        Integer categoryId,

        @Positive(message = "Price must be greater than zero")
        BigDecimal price,

        @Positive(message = "Amount of products must be positive")
        Integer amount

) {
}
