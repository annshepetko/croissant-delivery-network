package com.delivery.product.services;

import com.delivery.product.entity.Product;
import com.delivery.product.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEntityService {

    private final ProductRepository productRepository;
    // todo index for product and address
    public Product getProductById(Long id){
    //    return productRepository.findById(id);
        return null;
    }
}
