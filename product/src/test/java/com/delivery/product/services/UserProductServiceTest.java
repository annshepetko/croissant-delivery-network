package com.delivery.product.services;

import static org.junit.jupiter.api.Assertions.*;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.entity.Product;
import com.delivery.product.mapper.ProductMapper;
import com.delivery.product.repo.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProductServiceTest {

    @Mock
    private ProductEntityService productEntityService;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UserProductService userProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductById_success() {
        // Arrange
        Long productId = 1L;
        Product product = Product.builder()
                .id(productId)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100))
                .build();
        ProductDto productDto = ProductDto.builder()
                .id(productId)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100))
                .build();

        when(productEntityService.getProductById(productId)).thenReturn(product);
        when(productMapper.convertToProductDto(product)).thenReturn(productDto);

        // Act
        ProductDto result = userProductService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.id());
        assertEquals("Test Product", result.name());
        verify(productEntityService, times(1)).getProductById(productId);
        verify(productMapper, times(1)).convertToProductDto(product);
    }

    @Test
    void getAllByCategory_success() {
        // Arrange
        Integer categoryId = 1;
        Pageable pageable = PageRequest.of(0, 10);
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100))
                .build();
        List<ProductDto> productList = List.of(productDto);
        Page<ProductDto> productPage = new PageImpl<>(productList, pageable, productList.size());

        when(productRepository.findAllByCategoryId(categoryId, pageable)).thenReturn(productPage);

        // Act
        Page<ProductDto> result = userProductService.getAllByCategory(categoryId, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(productDto, result.getContent().get(0));
        verify(productRepository, times(1)).findAllByCategoryId(categoryId, pageable);
    }
}
