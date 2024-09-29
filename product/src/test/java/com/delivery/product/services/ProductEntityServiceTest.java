package com.delivery.product.services;

import com.delivery.product.entity.Product;
import com.delivery.product.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductEntityServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductEntityService productEntityService;

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
                .build();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product result = productEntityService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Test Product", result.getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductById_notFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productEntityService.getProductById(productId);
        });

        assertEquals("Product with this id is not found", exception.getMessage());
        verify(productRepository, times(1)).findById(productId);
    }
}
