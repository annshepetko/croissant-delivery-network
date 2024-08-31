package com.delivery.order.mapper;

import com.delivery.order.dto.OrderProductRequest;
import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.OrderedProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    public List<OrderedProduct> mapToOrderedProduct(List<OrderProductRequest> requestList, Order order){
        return requestList.stream()
                .map(e -> OrderedProduct.builder()
                        .productId(e.productId())
                        .name(e.name())
                        .price(e.price())
                        .amount(e.amount())
                        .categoryId(e.categoryId())
                        .order(order)
                        .build())
                .toList();
    }

    public List<OrderProductRequest> mapToOrderedProductRequests(List<OrderedProduct> requestList){
        return requestList.stream()
                .map(e -> OrderProductRequest.builder()
                        .productId(e.getProductId())
                        .name(e.getName())
                        .price(e.getPrice())
                        .amount(e.getAmount())
                        .categoryId(e.getCategoryId())
                        .build())
                .toList();
    }

    @Builder
    @Getter
    public static class OrderBody {

        private PerformOrderRequest performOrderRequest;
        private String email;
        private BigDecimal price;

        public OrderBody(PerformOrderRequest performOrderRequest, String email, BigDecimal price) {
            this.performOrderRequest = performOrderRequest;
            this.email = email;
            this.price = price;
        }
    }


}
