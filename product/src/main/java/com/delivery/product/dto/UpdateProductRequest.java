package com.delivery.product.dto;

public record UpdateProductRequest (
        String productName, ProductDto productData
){
}
