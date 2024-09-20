package com.delivery.order.controller;

import com.delivery.order.dto.admin.OrderBaseDto;
import com.delivery.order.dto.admin.OrderPageAdminDto;
import com.delivery.order.entity.status.OrderStatus;
import com.delivery.order.service.OrderAdminService;
import lombok.RequiredArgsConstructor;
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


    private final OrderAdminService adminService;

    @GetMapping("/all")
    public ResponseEntity<Page<OrderBaseDto>> hello(

            @RequestParam(value = "number", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "25") int size,
            @RequestParam(value = "sorting", defaultValue = "createdAt") String sorting,
            @RequestParam(value = "sortBy", defaultValue = "ASC") String direction,
            @RequestParam(value = "status", defaultValue = "PREPARING") OrderStatus status
    ){


        Sort sort = Sort.by(Sort.Direction.fromString(direction), sorting);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(adminService.getAllOrders(status, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderPageAdminDto> getOrderById(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.getOrderById(id));
    }

    @PatchMapping("/status/{id}")
    public void changeOrderStatus(
            @PathVariable("id") Long orderId,
            @RequestParam("status") OrderStatus orderStatus
    ){
        adminService.changeOrderStatus(orderId, orderStatus);

    }
}
