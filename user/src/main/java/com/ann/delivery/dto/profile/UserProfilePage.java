package com.ann.delivery.dto.profile;

import com.ann.delivery.dto.order.OrderDto;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record UserProfilePage(

        String userFirstname,
        String userLastname,
        int numberOrders,
        Double bonuses,
        Page<OrderDto> orders
) {
}
