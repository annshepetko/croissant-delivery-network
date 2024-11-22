package com.delivery.order.controller;

import com.delivery.order.dto.admin.OrderBaseDto;
import com.delivery.order.dto.admin.OrderPageAdminDto;
import com.delivery.order.entity.status.OrderStatus;
import com.delivery.order.service.OrderAdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order/admin")
public class OrderAdminController {

    private static final Logger logger = LoggerFactory.getLogger(OrderAdminController.class);

    private final OrderAdminService adminService;

    @GetMapping("/all")
    public ResponseEntity<Page<OrderBaseDto>> getAllOrders(

            @RequestParam(value = "number", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "25") int size,
            @RequestParam(value = "sorting", defaultValue = "createdAt") String sorting,
            @RequestParam(value = "sortBy", defaultValue = "ASC") String direction,
            @RequestParam(value = "status", defaultValue = "PREPARING") OrderStatus status
    ){
        logger.info("Fetching all orders with status: {}, page: {}, size: {}, sorting: {}, direction: {}",
                status, page, size, sorting, direction);

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sorting);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OrderBaseDto> orders = adminService.getAllOrders(status, pageable);

        logger.debug("Retrieved {} orders", orders.getTotalElements());

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderPageAdminDto> getOrderById(@PathVariable("id") Long id){
        logger.info("Fetching order by ID: {}", id);

        OrderPageAdminDto order = adminService.getOrderById(id);
        logger.debug("Order details: {}", order);


        return ResponseEntity.ok(order);
    }

    @PatchMapping("/status/{id}")
    public void changeOrderStatus(
            @PathVariable("id") Long orderId,
            @RequestParam("status") OrderStatus orderStatus
    ){
        logger.info("Changing order status. Order ID: {}, New status: {}", orderId, orderStatus);

        adminService.changeOrderStatus(orderId, orderStatus);

        logger.debug("Order status changed successfully for Order ID: {}", orderId);
    }
}
