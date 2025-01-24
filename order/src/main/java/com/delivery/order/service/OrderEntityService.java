package com.delivery.order.service;

import com.delivery.order.entity.Address;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.status.OrderStatus;
import com.delivery.order.mapper.AddressMapper;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.repo.AddressRepository;
import com.delivery.order.repo.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderEntityService {

    private static final Logger logger = LoggerFactory.getLogger(OrderEntityService.class);

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AddressRepository addressRepository;

    @Transactional
    public Order saveOrder(Order orderBody) {

        Order order = orderRepository.save(orderBody);

        logger.info("SAVED ORDER : {}", order.getId() + "at : {}", LocalDateTime.now());

        return order ;

    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> {

            logger.error("FAILED TO FIND ORDER BY ID :{}", id , "ORDER NOT FOUND");
            return new EntityNotFoundException("Order with this id is not exist");
        });
    }

    public Order save(Order order) {

        logger.info("SAVING ORDER");

        return orderRepository.save(order);
    }
}
