package com.delivery.product.controller;

import com.delivery.product.dto.CategoryDto;
import com.delivery.product.dto.ProductDto;
import com.delivery.product.services.CategoryService;
import com.delivery.product.services.UserProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/products/user")
public class UserProductController {

    private static final Logger logger = LoggerFactory.getLogger(UserProductController.class);

    private final UserProductService userProductService;
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id) {

        logger.info("Fetching product with ID: {}", id);

        ProductDto productDto = userProductService.getProductById(id);

        logger.info("Successfully fetched product: {}", productDto);

        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {

        logger.info("Fetching all categories");

        List<CategoryDto> categories = categoryService.getAllCategories();
        logger.info("Successfully fetched categories: {}", categories);

        return ResponseEntity.ok(categories);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAll(
            @RequestParam(value = "categoryId", required = false, defaultValue = "1") Integer categoryId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size
    ) {
        logger.info("Fetching products for category ID: {}, page: {}, size: {}", categoryId, page, size);

        Pageable pageableToPass = PageRequest.of(page, size);

        Page<ProductDto> productPage = userProductService.getAllByCategory(categoryId, pageableToPass);

        logger.info("Successfully fetched products: {}", productPage);
        return ResponseEntity.ok(productPage);
    }
}
