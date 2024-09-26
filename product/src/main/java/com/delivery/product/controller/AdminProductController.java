package com.delivery.product.controller;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.dto.admin.CreateProductRequest;
import com.delivery.product.services.AdminProductService;
import com.delivery.product.services.UserProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final UserProductService userProductService;
    private final AdminProductService adminProductService;

    @PostMapping
    public void create(@RequestBody CreateProductRequest createProductRequest){

        adminProductService.createProduct(createProductRequest);
    }

    @PatchMapping("/{id}")
    public void updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") Long id){

        adminProductService.patchProduct(productDto, id);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAll(
            @RequestParam(value = "categoryId", required = false, defaultValue = "1") Integer categoryId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue  = "2") Integer size
    ){
        Pageable pageableToPass = PageRequest.of(page, size);

        return ResponseEntity.ok(userProductService.getAllByCategory(categoryId, pageableToPass));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        adminProductService.deleteProduct(id);
    }

}
