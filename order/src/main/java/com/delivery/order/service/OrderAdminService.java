package com.delivery.order.service;

import com.delivery.order.dto.admin.OrderBaseDto;
import com.delivery.order.dto.admin.OrderPageAdminDto;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.status.OrderStatus;
import com.delivery.order.exception.DeniedChangeStatusException;
import com.delivery.order.mapper.OrderAdminMapper;
import com.delivery.order.repo.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderAdminService {

    private final OrderEntityService orderEntityService;
    private final OrderAdminMapper orderAdminMapper;
    private final OrderRepository orderRepository;

    public Page<OrderBaseDto> getAllOrders(OrderStatus status, Pageable pageable){

        return orderRepository.findAllOrdersAndMapToOrderBase(status, pageable);
    }


    public OrderPageAdminDto getOrderById(Long id) {

        Order order = orderEntityService.findById(id);

        return orderAdminMapper.mapToOrderAdminPage(order);
    }

    @Transactional(readOnly = true)
    public void changeOrderStatus(Long orderId, OrderStatus orderStatus) {

        Order order = orderEntityService.findById(orderId);

        if (!order.getOrderStatus().equals(OrderStatus.DELIVERED)){

            order.setOrderStatus(orderStatus);
            orderEntityService.save(order);
        }
        throw new DeniedChangeStatusException("Order is already delivered, refused to change the status");
    }
}
