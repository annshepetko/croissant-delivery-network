package com.delivery.product.repo;

import com.delivery.product.dto.ProductDto;
import com.delivery.product.entity.Category;
import com.delivery.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    @Query("select new com.delivery.product.dto.ProductDto(p.id, p.name, p.description, p.price, p.photoUrl, " +
            "new com.delivery.product.dto.CategoryDto(c.id, c.name), p.createdAt, p.updatedAt) " +
            "from Product p join p.category c WHERE c.id = :categoryId")
    Page<ProductDto> findAllByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

}
