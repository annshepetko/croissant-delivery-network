package com.delivery.order.repo;

import com.delivery.order.dto.admin.OrderBaseDto;
import com.delivery.order.dto.user.OrderUserServiceDto;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.status.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class OrderRepositoryTest {


    @Autowired
    private OrderRepository orderRepository;

    private Order order;
    private OrderUserServiceDto orderUserServiceDto;

    @BeforeEach
    void init(){
        this.orderUserServiceDto = new OrderUserServiceDto(LocalDateTime.now(), 1L, BigDecimal.TEN);
        this.order = Order.builder()
                .id(1L)
                .orderStatus(OrderStatus.ACCEPTED)
                .email("ann.ssh@gmail.com")
                .createdAt(LocalDateTime.now())
                .totalPrice(BigDecimal.TEN)
                .build();
    }

    @Test
    void findAllByEmailAndMap() {


        Order savedOrder = orderRepository.save(order);
        Pageable pageable = PageRequest.of(1, 1);

        Page<OrderUserServiceDto> pageOfUserDto = orderRepository.findAllByEmailAndMap(savedOrder.getEmail(), pageable).get();
        Iterator iterable = pageOfUserDto.iterator();

        while (iterable.hasNext()){
            OrderUserServiceDto orderUserServiceDto1 = (OrderUserServiceDto) iterable.next();

            Assertions.assertEquals(orderUserServiceDto1.orderedAt(), orderUserServiceDto.orderedAt());
            Assertions.assertEquals(orderUserServiceDto1.id(), orderUserServiceDto.id() );
            Assertions.assertEquals(orderUserServiceDto1.totalPrice(), orderUserServiceDto.totalPrice());
        }

    }


    @Test
    void findAllOrdersAndMapToOrderBase() {

        Order savedOrder =  orderRepository.save(order);

        Pageable pageable = PageRequest.of(1, 1);

        Page<OrderBaseDto> pageOfUserDto = orderRepository.findAllOrdersAndMapToOrderBase(OrderStatus.ACCEPTED, pageable);

        Iterator iterable = pageOfUserDto.iterator();

        while (iterable.hasNext()){

             OrderBaseDto orderBase = (OrderBaseDto) iterable.next();

            Assertions.assertEquals(orderBase.getId(), savedOrder.getId());
            Assertions.assertEquals(orderBase.getTotalPrice(), savedOrder.getTotalPrice());
            Assertions.assertEquals(orderBase.getStatus(), savedOrder.getOrderStatus());

        }


    }
}