package com.delivery.product.services;

import com.delivery.product.entity.Category;
import com.delivery.product.repo.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findById(Integer id){
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This category does not exist"));
    }
}
