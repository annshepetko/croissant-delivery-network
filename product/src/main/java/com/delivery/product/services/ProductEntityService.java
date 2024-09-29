package com.delivery.product.services;

import com.delivery.product.entity.Product;
import com.delivery.product.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEntityService {

    private static final Logger logger = LoggerFactory.getLogger(ProductEntityService.class);

    private final ProductRepository productRepository;

    public Product getProductById(Long id) {
        logger.info("Fetching product with ID: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product with ID: {} not found", id);
                    return new EntityNotFoundException("Product with this id is not found");
                });
    }
}
