package com.delivery.product.mapper;


import com.delivery.product.dto.ProductDto;
import com.delivery.product.entity.Product;
import com.delivery.product.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryService categoryService;

    public Product convertToProduct(ProductDto productDto){

        return Product.builder()
                .name(productDto.name())
                .price(productDto.price())
                .category(categoryService.findById(productDto.categoryId()))
                .description(productDto.description())
                .photoUrl(productDto.photoUrl())
                .build();
    }

    public void patchProduct(Product product, ProductDto productDto){

        product.setCategory(categoryService.findById(productDto.categoryId()));
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setDescription(productDto.description());
        product.setPhotoUrl(productDto.photoUrl());

    }

}

