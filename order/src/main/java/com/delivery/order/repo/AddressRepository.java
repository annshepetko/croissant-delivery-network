package com.delivery.order.repo;

import com.delivery.order.dto.address.AddressDto;
import com.delivery.order.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {


}