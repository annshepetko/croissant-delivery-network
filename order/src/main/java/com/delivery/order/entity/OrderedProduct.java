package com.delivery.order.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProduct {

    private Long id;

    private String name;

    private BigDecimal price;

    private Integer categoryId;

    private int amount;

    @ManyToOne
    private Order order;
}
