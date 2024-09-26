package com.delivery.product.mapper;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.dto.admin.CreateProductRequest;
import com.delivery.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminProductMapper {

    private final CategoryMapper categoryMapper;

    public Product convertToProduct(CreateProductRequest createProductRequest) {

        return Product.builder()
                .name(createProductRequest.name())
                .price(createProductRequest.price())
                .photoUrl(createProductRequest.photoUrl())
                .description(createProductRequest.description())
                .category(categoryMapper.convertToCategory(createProductRequest.category()))
                .build();

    }


}
