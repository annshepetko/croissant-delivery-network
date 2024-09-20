package com.delivery.order.mapper;

import com.delivery.order.dto.address.AddressDto;
import com.delivery.order.entity.Address;
import org.springframework.stereotype.Component;

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

    public AddressDto mapToAddressDto(Address address){

        return AddressDto.builder()
                .city(address.getCity())
                .id(address.getId())
                .house(address.getHouse())
                .street(address.getStreet())
                .floor(address.getFloor())
                .flat(address.getFlat())
                .district(address.getDistrict())
                .entrance(address.getEntrance())
                .build();
    }

}
