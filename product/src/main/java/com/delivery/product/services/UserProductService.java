package com.delivery.product.services;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.mapper.ProductMapper;
import com.delivery.product.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductDto getProductById(Long id) {
        var product = productRepository.findById(id).orElseThrow();


        return productMapper.convertToProductDto(product);
    }


    public Page<ProductDto> getAllByCategory(Integer categoryId, Pageable pageable) {
        return productRepository.findAllByCategoryId(categoryId, pageable);
    }
}
