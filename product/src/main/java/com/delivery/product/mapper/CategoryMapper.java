package com.delivery.product.mapper;

import com.delivery.product.dto.CategoryDto;
import com.delivery.product.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto convertToDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
