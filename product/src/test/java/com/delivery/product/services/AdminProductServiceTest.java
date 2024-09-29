package com.delivery.product.services;

import com.delivery.product.dto.CategoryDto;
import com.delivery.product.dto.ProductDto;
import com.delivery.product.dto.admin.CreateProductRequest;
import com.delivery.product.entity.Product;
import com.delivery.product.mapper.AdminProductMapper;
import com.delivery.product.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private AdminProductMapper productMapper;

    @InjectMocks
    private AdminProductService adminProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_success() {
        // Arrange
        CategoryDto categoryDto = new CategoryDto(1, "Electronics");
        CreateProductRequest request = new CreateProductRequest(
                "Phone", "A smartphone", BigDecimal.valueOf(299.99), "http://example.com/photo.jpg", categoryDto
        );
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .photoUrl(request.photoUrl())
                .category(null) // Assume the category is resolved in the mapper or somewhere else
                .build();
        when(productMapper.convertToProduct(request)).thenReturn(product);

        // Act
        adminProductService.createProduct(request);

        // Assert
        verify(productMapper, times(1)).convertToProduct(request);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void patchProduct_success() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = Product.builder()
                .id(productId)
                .name("Old Name")
                .description("Old Description")
                .price(BigDecimal.valueOf(100))
                .build();
        ProductDto productDto = new ProductDto(
                productId, "New Name", "New Description", BigDecimal.valueOf(299.99),
                "http://example.com/new-photo.jpg",
                new CategoryDto(1, "Electronics"),
                null, null
        );
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Act
        adminProductService.patchProduct(productDto, productId);

        // Assert
        assertEquals("New Name", existingProduct.getName());
        assertEquals("New Description", existingProduct.getDescription());
        assertEquals(BigDecimal.valueOf(299.99), existingProduct.getPrice());
        assertEquals("http://example.com/new-photo.jpg", existingProduct.getPhotoUrl());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void patchProduct_notFound() {
        Long productId = 1L;
        ProductDto productDto = new ProductDto(productId, "New Name", "New Description", BigDecimal.valueOf(299.99),
                "http://example.com/new-photo.jpg", null, null, null);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> adminProductService.patchProduct(productDto, productId));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void deleteProduct_success() {
        // Arrange
        Long productId = 1L;

        // Act
        adminProductService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void patchProductState_success() {
        // Arrange
        Product product = new Product();
        ProductDto productDto = new ProductDto(
                null, "New Name", "New Description", BigDecimal.valueOf(299.99),
                "http://example.com/new-photo.jpg",
                new CategoryDto(1, "Electronics"),
                null, null
        );
        when(categoryService.findById(productDto.category().id())).thenReturn(null); // Assuming category is resolved by ID

        // Act
        adminProductService.patchProductState(product, productDto);

        // Assert
        assertEquals("New Name", product.getName());
        assertEquals("New Description", product.getDescription());
        assertEquals(BigDecimal.valueOf(299.99), product.getPrice());
        assertEquals("http://example.com/new-photo.jpg", product.getPhotoUrl());
        verify(categoryService, times(1)).findById(productDto.category().id());
    }
}
