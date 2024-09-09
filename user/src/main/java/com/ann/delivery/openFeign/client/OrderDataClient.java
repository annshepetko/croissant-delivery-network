package com.ann.delivery.openFeign.client;


import com.ann.delivery.dto.OrderDto;
import com.ann.delivery.openFeign.client.config.OrderFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "ORDER-SERVICE", url = "http://localhost:8060", configuration = OrderFeignConfiguration.class)
public interface OrderDataClient {

    @GetMapping("/api/order/all/{name}")
    ResponseEntity<Optional<List<OrderDto>>> getAllUserOrders(@PathVariable("name") String username);
}
