package com.delivery.order.service;

import com.delivery.order.dto.OrderDto;
import com.delivery.order.entity.Order;
import com.delivery.order.mapper.OrderUserMapper;
import com.delivery.order.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderUserService {

    private final OrderUserMapper orderUserMapper;
    private final OrderRepository orderRepository;

    public Optional<List<OrderDto>> getAllUserOrders(String name) {

        List<Order> orders = orderRepository.findAllByEmail(name).orElseThrow();

        return Optional.of(orderUserMapper.mapToOrderUserPageDto(orders));
    }

}
