package com.delivery.order.service.price;

import com.delivery.order.dto.Bonuses;
import com.delivery.order.service.price.interfaces.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthDiscountService implements DiscountService {


    public BigDecimal calculateTotalPrice(BigDecimal generalPrice, Bonuses bonuses) {

        return handleBonuses(generalPrice, bonuses);
    }

    private BigDecimal handleBonuses(BigDecimal price, Bonuses bonuses){

        if (bonuses.getIsWriteOff()){
            return reducePrice(price, bonuses);
        }
        refreshBonuses(bonuses);
        return price;
        
    }

    private BigDecimal reducePrice(BigDecimal price, Bonuses bonuses) {
        bonuses.writeOffBonuses();
        return price.subtract(BigDecimal.valueOf(bonuses.getBonuses()));
    }

    private void refreshBonuses(Bonuses bonuses) {
        bonuses.addBonuses(4.0);
    }


}
