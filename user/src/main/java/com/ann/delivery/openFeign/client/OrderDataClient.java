package com.ann.delivery.openFeign.client;


import com.ann.delivery.dto.order.OrderDto;
import com.ann.delivery.openFeign.client.config.OrderFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "ORDER-SERVICE", url = "http://localhost:8060", configuration = OrderFeignConfiguration.class)
public interface OrderDataClient {

    @GetMapping("/api/order/all/{name}")
    ResponseEntity<Optional<Page<OrderDto>>> getAllUserOrders(
            @PathVariable("name") String username,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue  = "2") Integer size
    );
}
