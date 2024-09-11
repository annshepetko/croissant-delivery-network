package com.delivery.order.repo;

import com.delivery.order.dto.OrderDto;
import com.delivery.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select new com.delivery.order.dto.OrderDto(o.createdAt, o.id, o.totalPrice) from Order o where o.email = :email")
    Optional<Page<OrderDto>> findAllByEmail(String email, Pageable pageable);
}