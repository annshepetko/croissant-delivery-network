package com.delivery.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
@Getter
@Slf4j
public class Bonuses {

    private static final Logger logger = LoggerFactory.getLogger(Bonuses.class);


    private Double bonuses;
    private Boolean isWriteOff;

    public Bonuses(Double bonuses, Boolean isWriteOff) {
        this.bonuses = bonuses;
        this.isWriteOff = isWriteOff;
    }


    private static Bonuses createBonusWriteOff(OrderBody orderRequest) {

        return new Bonuses(
                orderRequest.getUserBonuses(),
                orderRequest.isWriteOffBonuses()
        );
    }

    public static Bonuses buildBonuses(OrderBody orderRequest) {

        return createBonusWriteOff(orderRequest);
    }

    public void addBonuses(Double extraBonuses){
        this.bonuses += extraBonuses;
    }

    public void writeOffBonuses(){
        this.bonuses = 0D;
    }

}
