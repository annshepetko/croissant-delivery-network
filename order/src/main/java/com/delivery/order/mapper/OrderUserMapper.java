package com.delivery.order.mapper;

import com.delivery.order.dto.OrderDto;
import com.delivery.order.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderUserMapper {


    public List<OrderDto> mapToOrderUserPageDto(List<Order> orders) {

        return orders.stream()
                .map(order -> OrderDto.builder()
                        .id(order.getId())
                        .orderedAt(order.getCreatedAt())
                        .totalPrice(order.getTotalPrice())
                        .build()
                )
                .toList();
    }

}
