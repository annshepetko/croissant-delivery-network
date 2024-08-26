package com.delivery.product.repo;

import com.delivery.product.dto.CategoryDto;
import com.delivery.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select new com.delivery.product.dto.CategoryDto(c.id, c.name) from Category c")
    List<CategoryDto> findAllCategories();
}