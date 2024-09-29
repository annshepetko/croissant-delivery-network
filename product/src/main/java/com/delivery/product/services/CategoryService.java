package com.delivery.product.services;

import com.delivery.product.dto.CategoryDto;
import com.delivery.product.entity.Category;
import com.delivery.product.repo.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    public Category findById(Integer id) {

        logger.info("Finding category with ID: {}", id);

        return categoryRepository.findById(id).orElseThrow(() -> {

            logger.error("Category with ID {} not found", id);
            return new EntityNotFoundException("This category does not exist");
        });
    }

    public List<CategoryDto> getAllCategories() {

        logger.info("Retrieving all categories");

        List<CategoryDto> categories = categoryRepository.findAllCategories();
        logger.info("Retrieved {} categories", categories.size());

        return categories;
    }
}
