package com.delivery.order.mapper;

import com.delivery.order.dto.OrderRequest;
import com.delivery.order.dto.OrderBody;
import com.delivery.order.service.price.AuthPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderBodyMapper {

    private final AuthPriceService authPriceService;

    public OrderBody createOrderBody(OrderRequest orderRequest, String email){
        return new OrderBody(orderRequest, calculatePrice(orderRequest),  email);
    }

    private BigDecimal calculatePrice(OrderRequest orderRequest) {
        return authPriceService.calculateGeneralPrice(orderRequest.orderProductDtos());
    }
}
