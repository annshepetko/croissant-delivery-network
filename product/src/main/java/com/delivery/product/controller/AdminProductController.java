package com.delivery.product.controller;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.dto.admin.CreateProductRequest;
import com.delivery.product.services.AdminProductService;
import com.delivery.product.services.UserProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/admin")
public class AdminProductController {

    private static final Logger logger = LoggerFactory.getLogger(AdminProductController.class);

    private final UserProductService userProductService;
    private final AdminProductService adminProductService;

    @PostMapping
    public void create(@RequestBody CreateProductRequest createProductRequest) {

        logger.info("Creating product: {}", createProductRequest);

        adminProductService.createProduct(createProductRequest);

        logger.info("Product created successfully");
    }

    @PatchMapping("/{id}")
    public void updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") Long id) {

        logger.info("Updating product with ID: {}", id);

        adminProductService.patchProduct(productDto, id);

        logger.info("Product with ID: {} updated successfully", id);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAll(
            @RequestParam(value = "categoryId", required = false, defaultValue = "1") Integer categoryId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size
    ) {
        Pageable pageableToPass = PageRequest.of(page, size);

        logger.info("Fetching products for category ID: {}, page: {}, size: {}", categoryId, page, size);

        Page<ProductDto> products = userProductService.getAllByCategory(categoryId, pageableToPass);

        logger.info("Fetched {} products for category ID: {}", products.getSize(), categoryId);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {

        logger.info("Deleting product with ID: {}", id);

        adminProductService.deleteProduct(id);

        logger.info("Product with ID: {} deleted successfully", id);
    }

}
