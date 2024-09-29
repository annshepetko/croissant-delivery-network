package com.delivery.product.services;

import static org.junit.jupiter.api.Assertions.*;

import com.delivery.product.dto.CategoryDto;
import com.delivery.product.entity.Category;
import com.delivery.product.repo.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_success() {
        Category category = Category.builder()
                .id(1)
                .name("Electronics")
                .build();
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.findById(1);

        assertNotNull(foundCategory);
        assertEquals(1, foundCategory.getId());
        assertEquals("Electronics", foundCategory.getName());
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    void findById_notFound() {
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.findById(1);
        });

        assertEquals("This category does not exist", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    void getAllCategories_success() {
        List<CategoryDto> categoryDtoList = Arrays.asList(
                CategoryDto.builder().id(1).name("Electronics").build(),
                CategoryDto.builder().id(2).name("Books").build()
        );
        when(categoryRepository.findAllCategories()).thenReturn(categoryDtoList);

        List<CategoryDto> allCategories = categoryService.getAllCategories();

        assertNotNull(allCategories);
        assertEquals(2, allCategories.size());
        assertEquals("Electronics", allCategories.get(0).name());
        assertEquals("Books", allCategories.get(1).name());
        verify(categoryRepository, times(1)).findAllCategories();
    }
}
