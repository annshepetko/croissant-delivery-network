package com.delivery.order.mapper;

import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.dto.user.OrderPageUserDto;
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

    public List<OrderedProduct> mapToOrderedProduct(List<OrderProductDto> requestList, Order order){
        return requestList.stream()
                .map(e -> OrderedProduct.builder()
                        .productId(e.productId())
                        .name(e.name())
                        .price(e.price())
                        .amount(e.amount())
                        .categoryId(e.categoryName())
                        .order(order)
                        .build())
                .toList();
    }

    public List<OrderProductDto> mapToOrderedProductRequests(List<OrderedProduct> requestList){
        return requestList.stream()
                .map(e -> OrderProductDto.builder()
                        .productId(e.getProductId())
                        .name(e.getName())
                        .price(e.getPrice())
                        .amount(e.getAmount())
                        .categoryName(e.getCategoryId())
                        .build())
                .toList();
    }

    public OrderPageUserDto mapToOrderPageUserDto(Order order) {

        return OrderPageUserDto.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt())
                .products(mapToOrderedProductRequests(order.getOrderedProducts()))
                .totalPrice(order.getTotalPrice())
                .status(order.getOrderStatus())
                .build();
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
