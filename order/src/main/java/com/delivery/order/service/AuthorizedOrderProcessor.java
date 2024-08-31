package com.delivery.order.service;


import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.entity.Order;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.openFeign.clients.OrderClient;
import com.delivery.order.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthorizedOrderProcessor {


    private final DiscountService discountService;
    private final OrderEntityService orderEntityService;

    public void processOrderFromAuthenticatedUser(
            PerformOrderRequest performOrderRequest,
            Long userId
    ){

        BigDecimal discountedOrderPrice = discountService.calculateTotalPrice(performOrderRequest.orderProductRequests(), 1.5);
        OrderMapper.OrderBody orderBody = new OrderMapper.OrderBody(performOrderRequest, userId, discountedOrderPrice);

        orderEntityService.saveOrder(orderBody);

    }

}
