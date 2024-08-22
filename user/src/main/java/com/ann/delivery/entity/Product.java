package com.ann.delivery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Product extends BaseEntity{

    @ManyToOne
    private Category category;

    private String name;

    private String warehouse;

    private Double price;

    private String description;


    public void setCategory(Category category){
        category.getProducts().add(this);
        this.category = category;
    }
}
