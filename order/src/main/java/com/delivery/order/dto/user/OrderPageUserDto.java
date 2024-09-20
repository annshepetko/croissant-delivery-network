package com.delivery.order.dto.user;

import com.delivery.order.dto.admin.OrderBaseDto;
import com.delivery.order.dto.product.OrderProductDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class OrderPageUserDto extends OrderBaseDto {
    private List<OrderProductDto> products;
}
