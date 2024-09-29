package com.delivery.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(min = 4, max = 64)
    @NotNull
    @NotEmpty
    private String name;

    @Size(max = 100)
    @NotNull
    @NotEmpty
    private String description;

    @Positive
    private BigDecimal price;

    @URL
    private String photoUrl;

    @ManyToOne
    @JoinColumn
    private Category category;

    @CreatedDate
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
