package com.delivery.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "order")
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(generator = "address_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "address_seq_gen", allocationSize = 1, sequenceName = "address_seq")
    private Long id;

    @NotNull
    private String city;

    @NotNull
    private String district;

    @NotNull
    private String street;

    @NotNull
    private String house;

    @NotNull
    private String entrance;

    @NotNull
    private int floor;

    @NotNull
    private int flat;

    @OneToOne(mappedBy = "address")
    @JoinColumn
    private Order order;

    public void setOrder(Order order){

        order.setAddress(this);
        this.order = order;

    }

}
