package com.delivery.product.services;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.mapper.ProductMapper;
import com.delivery.product.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProductService {

    private static final Logger logger = LoggerFactory.getLogger(UserProductService.class);

    private final ProductEntityService productEntityService;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductDto getProductById(Long id) {

        logger.info("Fetching product with ID: {}", id);

        var product = productEntityService.getProductById(id);

        ProductDto productDto = productMapper.convertToProductDto(product);

        logger.info("Fetched product: {}", productDto);
        return productDto;
    }

    public Page<ProductDto> getAllByCategory(Integer categoryId, Pageable pageable) {

        logger.info("Fetching products for category ID: {}, page: {}, size: {}", categoryId, pageable.getPageNumber(), pageable.getPageSize());

        Page<ProductDto> productPage = productRepository.findAllByCategoryId(categoryId, pageable);

        return productPage;
    }
}
