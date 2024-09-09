package com.ann.delivery.dto;

import com.ann.delivery.dto.OrderDto;
import lombok.Builder;

import java.util.List;

@Builder
public record UserProfilePage(

        String userFirstname,
        String userLastname,
        int numberOrders,
        Double bonuses,
        List<OrderDto> orders
) {
}
