package com.delivery.order.service;

import com.delivery.order.dto.OrderDto;
import com.delivery.order.entity.Order;
import com.delivery.order.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderUserService {

    private final OrderRepository orderRepository;

    public Optional<Page<OrderDto>> getAllUserOrders(String name, Pageable pageable) {

        var orders = orderRepository.findAllByEmail(name, pageable);

        return orders;
    }

}
