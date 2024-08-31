package com.delivery.order.mapper;

import com.delivery.order.dto.AddressDto;
import com.delivery.order.entity.Address;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AddressMapper {

    public Address mapToAddress(AddressDto addressDto){
        return  Address.builder()
                .city(addressDto.city())
                .house(addressDto.house())
                .floor(addressDto.floor())
                .flat(addressDto.flat())
                .entrance(addressDto.entrance())
                .street(addressDto.street())
                .district(addressDto.district())
                .build();
    }

}
