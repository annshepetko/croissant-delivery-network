package com.ann.delivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "order-user")
public class OrderUser {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private Long orderId;

}
