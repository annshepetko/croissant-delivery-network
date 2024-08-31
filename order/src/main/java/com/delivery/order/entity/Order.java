package com.delivery.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
@ToString(exclude = "address")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq_gen")
    @SequenceGenerator(name = "orders_seq_gen", sequenceName = "orders_seq", allocationSize = 1)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "user_firstname")
    private String userFirstName;

    @Column(name = "user_lastname")
    private String userLastName;

    @Column( length = 10, name = "user_phone_number")
    private String userPhoneNumber;


    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderedProduct> orderedProducts;

    @Column(name = "total_price")
    @Positive
    private BigDecimal totalPrice;


    private String email;
}
