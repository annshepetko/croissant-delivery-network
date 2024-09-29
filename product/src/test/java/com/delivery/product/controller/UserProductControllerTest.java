package com.delivery.product.controller;

import static org.junit.jupiter.api.Assertions.*;


import com.delivery.product.dto.CategoryDto;
import com.delivery.product.dto.ProductDto;
import com.delivery.product.services.CategoryService;
import com.delivery.product.services.UserProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserProductController.class)
@AutoConfigureMockMvc
class UserProductControllerTest {

    @MockBean
    private UserProductService userProductService;

    @MockBean
    private CategoryService categoryService;


    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper(); // Initialize ObjectMapper
    }

    @Test
    void getProduct_success() throws Exception {
        // Arrange
        Long productId = 1L;
        ProductDto productDto = ProductDto.builder()
                .id(productId)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100))
                .build();

        when(userProductService.getProductById(productId)).thenReturn(productDto);

        String expectedResponseJson = objectMapper.writeValueAsString(productDto);

        // Act & Assert
        mockMvc.perform(get("/api/user/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson)); // Compare the JSON response

        verify(userProductService, times(1)).getProductById(productId);
    }

    @Test
    void getAllCategories_success() throws Exception {
        // Arrange
        List<CategoryDto> categories = List.of(
                new CategoryDto(1, "Category 1"),
                new CategoryDto(2, "Category 2")
        );

        when(categoryService.getAllCategories()).thenReturn(categories);

        String expectedResponseJson = objectMapper.writeValueAsString(categories);

        // Act & Assert
        mockMvc.perform(get("/api/user/products/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson)); // Compare the JSON response

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void getAllProductsByCategory_success() throws Exception {
        // Arrange
        Integer categoryId = 1;
        Pageable pageable = PageRequest.of(0, 2);
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100))
                .build();

        List<ProductDto> productList = List.of(productDto);
        Page<ProductDto> productPage = new PageImpl<>(productList, pageable, productList.size());

        when(userProductService.getAllByCategory(categoryId, pageable)).thenReturn(productPage);

        String expectedResponseJson = objectMapper.writeValueAsString(productPage);

        // Act & Assert
        mockMvc.perform(get("/api/user/products")
                        .param("categoryId", categoryId.toString())
                        .param("page", "0")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson)); // Compare the JSON response

        verify(userProductService, times(1)).getAllByCategory(categoryId, pageable);
    }
}
