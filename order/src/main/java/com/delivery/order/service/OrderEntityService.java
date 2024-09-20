package com.delivery.order.service;

import com.delivery.order.entity.Address;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.status.OrderStatus;
import com.delivery.order.mapper.AddressMapper;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.repo.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderEntityService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public Order saveOrder(OrderMapper.OrderBody orderBody){

        Order order = orderMapper.buildOrder(orderBody);

        return orderRepository.save(order);
    }

    public Order findById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order with this id is not exist"));
    }

    public Order save(Order order){

        return orderRepository.save(order);
    }
}
