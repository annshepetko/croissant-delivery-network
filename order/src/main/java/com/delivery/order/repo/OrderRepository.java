package com.delivery.order.repo;

import com.delivery.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT nextval('orders_seq')", nativeQuery = true)
    Long getNextOrderId();
}