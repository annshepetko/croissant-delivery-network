package com.delivery.product.controller;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.dto.UpdateProductRequest;
import com.delivery.product.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProductDto productDto){

        return productService.createProduct(productDto);
    }

    @PatchMapping
    public ResponseEntity<Void> updateProduct(@RequestBody UpdateProductRequest updateProductRequest){
        return productService.patchProduct(updateProductRequest);
    }

}
