package com.delivery.product.dto.admin;


import com.delivery.product.dto.CategoryDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record CreateProductRequest (
        @NotNull @Size(min = 4, max = 15) @NotEmpty String name,
        @NotNull @Size(max = 100) @NotEmpty String description,
        @Positive BigDecimal price,
        @URL String photoUrl,
        CategoryDto category
){
}
