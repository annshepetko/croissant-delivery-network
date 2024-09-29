package com.delivery.product.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.dto.admin.CreateProductRequest;
import com.delivery.product.services.AdminProductService;
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

@WebMvcTest(controllers = AdminProductController.class)
@AutoConfigureMockMvc
class AdminProductControllerTest {

    @MockBean
    private UserProductService userProductService;

    @MockBean
    private AdminProductService adminProductService;


    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper(); // Initialize the ObjectMapper
    }

    @Test
    void createProduct_success() throws Exception {
        // Arrange
        CreateProductRequest createProductRequest = new CreateProductRequest(
                "Test Product", "Test Description", BigDecimal.valueOf(100), "http://test.com", null
        );

        String expectedRequestJson = objectMapper.writeValueAsString(createProductRequest);

        // Act & Assert
        mockMvc.perform(post("/api/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedRequestJson))
                .andExpect(status().isOk());

        verify(adminProductService, times(1)).createProduct(any(CreateProductRequest.class));
    }

    @Test
    void updateProduct_success() throws Exception {
        // Arrange
        Long productId = 1L;
        ProductDto productDto = ProductDto.builder()
                .id(productId)
                .name("Updated Product")
                .description("Updated Description")
                .price(BigDecimal.valueOf(150))
                .build();

        String expectedProductJson = objectMapper.writeValueAsString(productDto);

        // Act & Assert
        mockMvc.perform(patch("/api/admin/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedProductJson))
                .andExpect(status().isOk());

        verify(adminProductService, times(1)).patchProduct(any(ProductDto.class), eq(productId));
    }

    @Test
    void getAllProducts_success() throws Exception {
        // Arrange
        Pageable pageable = PageRequest.of(0, 2);
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100))
                .build();
        List<ProductDto> productList = List.of(productDto);
        Page<ProductDto> productPage = new PageImpl<>(productList, pageable, productList.size());

        when(userProductService.getAllByCategory(1, pageable)).thenReturn(productPage);

        String expectedResponseJson = objectMapper.writeValueAsString(productPage);

        // Act & Assert
        mockMvc.perform(get("/api/admin/products")
                        .param("categoryId", "1")
                        .param("page", "0")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson)); // Compare the JSON with expected JSON

        verify(userProductService, times(1)).getAllByCategory(1, pageable);
    }

    @Test
    void deleteProduct_success() throws Exception {
        // Arrange
        Long productId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/admin/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(adminProductService, times(1)).deleteProduct(productId);
    }
}
