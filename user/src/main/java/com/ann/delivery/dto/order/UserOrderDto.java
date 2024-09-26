package com.ann.delivery.dto.order;

import lombok.Builder;

@Builder
public record UserOrderDto(
        String email,
        Double bonuses

) {

}
