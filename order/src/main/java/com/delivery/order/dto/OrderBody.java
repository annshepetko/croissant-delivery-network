package com.delivery.order.dto;

import com.delivery.order.dto.OrderRequest;
import com.delivery.order.dto.product.OrderProductDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class OrderBody {

    private OrderRequest orderRequest;

    @Setter
    private BigDecimal price;
    private String email;

    public OrderBody(OrderRequest orderRequest, BigDecimal price, String email) {
        this.orderRequest = orderRequest;
        this.price = price;
        this.email = email;
    }

    public Double getUserBonuses(){
        return this.orderRequest.userBonuses();
    }

    public boolean isWriteOffBonuses(){
        return  this.orderRequest.isWriteOffBonuses();
    }

    public List<OrderProductDto> getProducts(){
        return this.orderRequest.orderProductDtos();
    }
}

