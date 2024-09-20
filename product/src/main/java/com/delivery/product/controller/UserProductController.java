package com.delivery.product.controller;

import com.delivery.product.dto.CategoryDto;
import com.delivery.product.dto.ProductDto;
import com.delivery.product.services.CategoryService;
import com.delivery.product.services.UserProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/products")
public class UserProductController {

    private final UserProductService userProductService;
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok(userProductService.getProductById(id));
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
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
}
