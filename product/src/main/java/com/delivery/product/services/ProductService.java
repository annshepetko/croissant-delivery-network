package com.delivery.product.services;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.dto.UpdateProductRequest;
import com.delivery.product.mapper.ProductMapper;
import com.delivery.product.repo.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ResponseEntity<Void> createProduct(ProductDto productDto) {
        var product = productMapper.convertToProduct(productDto);
        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Void> patchProduct(UpdateProductRequest updateProductRequest) {
        var currentProduct = productRepository.findByName(updateProductRequest.productName()).orElseThrow();

        productMapper.patchProduct(currentProduct, updateProductRequest.productData());

        productRepository.save(currentProduct);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
