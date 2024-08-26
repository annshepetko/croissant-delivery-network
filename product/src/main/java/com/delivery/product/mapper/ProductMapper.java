package com.delivery.product.mapper;


import com.delivery.product.dto.ProductDto;
import com.delivery.product.entity.Product;
import com.delivery.product.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    public Product convertToProduct(ProductDto productDto){

        return Product.builder()
                .name(productDto.name())
                .price(productDto.price())
                .category(categoryService.findById(productDto.category().id()))
                .description(productDto.description())
                .photoUrl(productDto.photoUrl())
                .build();
    }

    public void patchProduct(Product product, ProductDto productDto){

        product.setCategory(categoryService.findById(productDto.category().id()));
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setDescription(productDto.description());
        product.setPhotoUrl(productDto.photoUrl());



    }

    public ProductDto convertToProductDto(Product product) {
        return  ProductDto.builder()
                .createdAt(product.getCreatedAt())
                .category(categoryMapper.convertToDto(product.getCategory()))
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .photoUrl(product.getPhotoUrl())
                .build();
    }
}

