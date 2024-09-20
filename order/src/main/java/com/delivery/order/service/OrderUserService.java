package com.delivery.order.service;

import com.delivery.order.dto.user.OrderPageUserDto;
import com.delivery.order.dto.user.OrderUserServiceDto;
import com.delivery.order.entity.Order;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderUserService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Optional<Page<OrderUserServiceDto>> getAllUserOrders(String name, Pageable pageable) {

        var orders = orderRepository.findAllByEmail(name, pageable);

        return orders;
    }

    public OrderPageUserDto getOrderPageForUser(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow();

        return orderMapper.mapToOrderPageUserDto(order);
    }
}
