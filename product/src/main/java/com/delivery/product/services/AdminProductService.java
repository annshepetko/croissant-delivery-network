package com.delivery.product.services;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.dto.admin.CreateProductRequest;
import com.delivery.product.entity.Product;
import com.delivery.product.mapper.AdminProductMapper;
import com.delivery.product.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private static final Logger logger = LoggerFactory.getLogger(AdminProductService.class);

    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final AdminProductMapper productMapper;

    public void createProduct(CreateProductRequest createProductRequest) {

        logger.info("Creating product with request: {}", createProductRequest);

        Product product = productMapper.convertToProduct(createProductRequest);
        productRepository.save(product);

        logger.info("Successfully created product: {}", product);
    }

    @Transactional(readOnly = true)
    public void patchProduct(ProductDto productDto, Long id) {

        logger.info("Updating product with ID: {}", id);

        var currentProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product with ID {} not found", id);
                    return new EntityNotFoundException("Product with this id is not found");
                });

        patchProductState(currentProduct, productDto);

        productRepository.save(currentProduct);

        logger.info("Successfully updated product: {}", currentProduct);
    }

    public void patchProductState(Product product, ProductDto productDto) {

        logger.info("Patching product state with new data: {}", productDto);

        product.setCategory(categoryService.findById(productDto.category().id()));
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setDescription(productDto.description());
        product.setPhotoUrl(productDto.photoUrl());

        logger.info("Product state updated: {}", product);
    }

    public void deleteProduct(Long id) {

        logger.info("Deleting product with ID: {}", id);

        productRepository.deleteById(id);
        logger.info("Successfully deleted product with ID: {}", id);
    }
}
