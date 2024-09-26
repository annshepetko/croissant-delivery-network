package com.delivery.product.services;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.dto.admin.CreateProductRequest;
import com.delivery.product.entity.Product;
import com.delivery.product.mapper.AdminProductMapper;
import com.delivery.product.mapper.ProductMapper;
import com.delivery.product.repo.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final AdminProductMapper productMapper;

    public void createProduct(CreateProductRequest createProductRequest) {

        Product product = productMapper.convertToProduct(createProductRequest);

        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public void patchProduct(ProductDto productDto, Long id) {
        var currentProduct = productRepository.findById(id).orElseThrow();

        patchProductState(currentProduct, productDto);

        productRepository.save(currentProduct);
    }

    public void patchProductState(Product product, ProductDto productDto){

        product.setCategory(categoryService.findById(productDto.category().id()));
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setDescription(productDto.description());
        product.setPhotoUrl(productDto.photoUrl());


    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


}
