package com.delivery.product.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.delivery.product.entity.Product}
 */
public record ProductDto(
        @NotNull @Size(min = 4, max = 15) @NotEmpty String name,
        @NotNull @Size(max = 100) @NotEmpty String description,
        @Positive BigDecimal price,
        @URL String photoUrl,
        Integer categoryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) implements Serializable {
}