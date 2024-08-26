package com.delivery.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String city;
    private String district;
    private String street;
    private String house;
    private String entrance;
    private int floor;
    private int flat;

    @OneToOne
    @MapsId
    private Order order;

}
