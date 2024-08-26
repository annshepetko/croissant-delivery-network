package com.delivery.product.dto;

import com.delivery.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductPage(

        String currentCategory,
        List<ProductDto> products,
        List<CategoryDto> categories
)
{
}
