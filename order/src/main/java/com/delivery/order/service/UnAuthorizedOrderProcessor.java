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
public class UnAuthorizedOrderProcessor {

    private final DiscountService discountService;
    private final OrderEntityService orderEntityService;

    public void processOrderFromNonAuthenticatedUser(PerformOrderRequest performOrderRequest) {
        BigDecimal price = discountService.calculateTotalPrice(performOrderRequest.orderProductRequests(), 0.0);
        OrderMapper.OrderBody orderBody = new OrderMapper.OrderBody(performOrderRequest, null, price);

        orderEntityService.saveOrder(orderBody);
    }


}
