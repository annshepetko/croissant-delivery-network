package com.delivery.product.services;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.mapper.ProductMapper;
import com.delivery.product.repo.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public void createProduct(ProductDto productDto) {
        var product = productMapper.convertToProduct(productDto);
        productRepository.save(product);

    }

    @Transactional(readOnly = true)
    public void patchProduct(ProductDto productDto, Long id) {
        var currentProduct = productRepository.findById(id).orElseThrow();

        productMapper.patchProduct(currentProduct, productDto);

        productRepository.save(currentProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
