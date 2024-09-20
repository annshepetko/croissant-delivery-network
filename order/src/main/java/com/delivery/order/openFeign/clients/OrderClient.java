package com.delivery.order.openFeign.clients;


import com.delivery.order.openFeign.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;


@FeignClient(name = "USER-SERVICE")
public interface OrderClient {

    @GetMapping("/api/user/is-registered")
    ResponseEntity<Optional<UserDto>> getUserId(@RequestHeader("Authorization") String token);

}

