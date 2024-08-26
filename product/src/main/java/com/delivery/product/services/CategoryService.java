package com.delivery.product.services;

import com.delivery.product.dto.CategoryDto;
import com.delivery.product.dto.ProductDto;
import com.delivery.product.entity.Category;
import com.delivery.product.repo.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findById(Integer id){
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This category does not exist"));
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAllCategories();
    }
}
