package com.delivery.order.repo;

import com.delivery.order.dto.user.OrderUserServiceDto;
import com.delivery.order.dto.admin.OrderBaseDto;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.status.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select new com.delivery.order.dto.user.OrderUserServiceDto(o.createdAt, o.id, o.totalPrice) from Order o where o.email = :email")
    Optional<Page<OrderUserServiceDto>> findAllByEmail(String email, Pageable pageable);


    @Query("select new com.delivery.order.dto.admin.OrderBaseDto(o.id, o.createdAt, o.totalPrice, o.orderStatus) from Order o where o.orderStatus = :status")
    Optional<Page<OrderBaseDto>> findAllOrders(OrderStatus status, Pageable pageable);





}
