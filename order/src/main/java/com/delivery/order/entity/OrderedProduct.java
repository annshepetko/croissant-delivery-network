package com.delivery.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ordered_products")
public class OrderedProduct {

    @Id
    @GeneratedValue(generator = "ordered_products_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ordered_products_seq_gen", sequenceName = "ordered_products_seq", allocationSize = 1)
    private Long id;

    private String name;

    private Long productId;
    private BigDecimal price;

    private Integer categoryId;

    private int amount;

    @ManyToOne
    @JoinColumn
    private Order order;
}
