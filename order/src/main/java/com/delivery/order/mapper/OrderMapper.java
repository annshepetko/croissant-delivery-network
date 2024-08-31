package com.delivery.order.mapper;

import com.delivery.order.dto.OrderProductRequest;
import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.entity.Address;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.OrderedProduct;
import com.delivery.order.repo.OrderRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final AddressMapper addressMapper;

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

    @Builder
    @Getter
    public static class OrderBody {

        private PerformOrderRequest performOrderRequest;
        private Long userId;
        private BigDecimal price;

        public OrderBody(PerformOrderRequest performOrderRequest, Long userId, BigDecimal price) {
            this.performOrderRequest = performOrderRequest;
            this.userId = userId;
            this.price = price;
        }
    }


}
