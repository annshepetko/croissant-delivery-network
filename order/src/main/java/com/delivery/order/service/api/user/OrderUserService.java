package com.delivery.order.service.api.user;

import com.delivery.order.dto.user.OrderPageUserDto;
import com.delivery.order.dto.user.OrderUserServiceDto;
import com.delivery.order.entity.Order;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.repo.OrderRepository;
import com.delivery.order.service.entity.OrderEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderUserService {

    private final OrderEntityService orderEntityService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Optional<Page<OrderUserServiceDto>> getAllUserOrders(String email, Pageable pageable) {
        log.info("Fetching orders for user: {}", email);
        log.debug("Pageable parameters: page number = {}, page size = {}", pageable.getPageNumber(), pageable.getPageSize());

        var orders = orderRepository.findAllByEmailAndMap(email, pageable);

        return orders;
    }

    public OrderPageUserDto getOrderPageForUser(Long orderId) {
        log.info("Fetching details for order ID: {}", orderId);

        Order order = orderEntityService.findById(orderId);


        log.info("Order found: {}, mapping to OrderPageUserDto", orderId);
        return orderMapper.mapToOrderPageUserDto(order);
    }
}
