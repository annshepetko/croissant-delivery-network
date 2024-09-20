package com.delivery.product.controller;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.services.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping
    public void create(@RequestBody ProductDto productDto){

        adminProductService.createProduct(productDto);
    }

    @PatchMapping("/{id}")
    public void updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") Long id){

        adminProductService.patchProduct(productDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        adminProductService.deleteProduct(id);
    }

}
