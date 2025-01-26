package com.delivery.order.service.price;

import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.dto.Bonuses;
import com.delivery.order.dto.OrderBody;
import com.delivery.order.service.price.interfaces.DiscountService;
import com.delivery.order.service.price.interfaces.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthPriceService implements PriceService {

    private DiscountService authDiscountService;

    public BigDecimal calculatePrice(OrderBody orderBody, Bonuses bonuses) {

        BigDecimal generalPrice = calculateGeneralPrice(orderBody.getProducts());

        return authDiscountService.calculateTotalPrice(generalPrice, bonuses);
    }

    public BigDecimal calculateGeneralPrice(List<OrderProductDto> products){
        return products.stream()
                .map(product -> product.price().multiply(BigDecimal.valueOf(product.amount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Autowired
    public void setAuthDiscountService(AuthDiscountService discountService) {
        this.authDiscountService = discountService;
    }

}
