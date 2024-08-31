package com.delivery.order.openFeign.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;


@FeignClient(name = "USER-SERVICE", url = "http://localhost:8080")
public interface OrderClient {

    @GetMapping("/api/v1/user/is-registered")
    ResponseEntity<Optional<String>> getUserId(@RequestHeader("Authorization") String token);

}

