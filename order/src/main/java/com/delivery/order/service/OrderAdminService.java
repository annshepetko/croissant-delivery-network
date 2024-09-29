package com.delivery.order.service;

import com.delivery.order.dto.admin.OrderBaseDto;
import com.delivery.order.dto.admin.OrderPageAdminDto;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.status.OrderStatus;
import com.delivery.order.exception.DeniedChangeStatusException;
import com.delivery.order.mapper.OrderAdminMapper;
import com.delivery.order.repo.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderAdminService {

    private static final Logger logger = LoggerFactory.getLogger(OrderAdminService.class);

    private final OrderEntityService orderEntityService;
    private final OrderAdminMapper orderAdminMapper;
    private final OrderRepository orderRepository;

    public Page<OrderBaseDto> getAllOrders(OrderStatus status, Pageable pageable){

        logger.debug("Getting orders page");

        return orderRepository.findAllOrdersAndMapToOrderBase(status, pageable);
    }


    public OrderPageAdminDto getOrderById(Long id) {

        Order order = orderEntityService.findById(id);

        logger.info("Getting order : {}", order.getId());

        return orderAdminMapper.mapToOrderAdminPage(order);
    }

    @Transactional(readOnly = true)
    public void changeOrderStatus(Long orderId, OrderStatus orderStatus) {

        logger.debug("STARTING TO CHANGE STATUS");

        Order order = orderEntityService.findById(orderId);

        if (order.getOrderStatus().equals(OrderStatus.DELIVERED)){
            logger.error("DENIED IN CHANGE STATUS at : {}", LocalDateTime.now());
            throw new DeniedChangeStatusException("Order is already delivered, refused to change the status");
        }

        order.setOrderStatus(orderStatus);

        logger.info("TRYING TO CHANGE ORDER STATUS to: {}", orderStatus.name() + "at :{}", LocalDateTime.now());
        orderEntityService.save(order);

    }
}
