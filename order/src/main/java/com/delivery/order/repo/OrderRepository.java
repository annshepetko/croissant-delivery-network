package com.delivery.order.repo;

import com.delivery.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {


    Optional<List<Order>> findAllByEmail(String email);
}