package com.delivery.order.service;

import com.delivery.order.dto.admin.OrderBaseDto;
import com.delivery.order.dto.admin.OrderPageAdminDto;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.status.OrderStatus;
import com.delivery.order.exception.DeniedChangeStatusException;
import com.delivery.order.mapper.OrderAdminMapper;
import com.delivery.order.repo.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderAdminService {


    private final OrderAdminMapper orderAdminMapper;
    private final OrderRepository orderRepository;

    public Page<OrderBaseDto> getAllOrders(OrderStatus status, Pageable pageable){

        return orderRepository.findAllOrders(status, pageable).orElseThrow(EntityNotFoundException::new);
    }


    public OrderPageAdminDto getOrderById(Long id) {

        Order order = orderRepository.findById(id).orElseThrow();
        return orderAdminMapper.mapToOrderAdminPage(order);
    }

    @Transactional(readOnly = true)
    public void changeOrderStatus(Long orderId, OrderStatus orderStatus) {

        Order order = orderRepository.findById(orderId).orElseThrow();

        if (!order.getOrderStatus().equals(OrderStatus.DELIVERED)){

            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
        }
        throw new DeniedChangeStatusException("Order is already delivered, refused to change the status");
    }
}
