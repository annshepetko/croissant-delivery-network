package com.ann.delivery.controller;


import com.ann.delivery.dto.order.UserOrderDto;
import com.ann.delivery.dto.profile.UserProfilePage;
import com.ann.delivery.services.UserOrderService;
import com.ann.delivery.services.UserPageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserOrderService userOrderService;
    private final UserPageService userPageService;

    @GetMapping("/bonuses")
    public ResponseEntity<Double> getUserBonuses(@RequestHeader(HttpHeaders.AUTHORIZATION) String token ){
        return ResponseEntity.ok(userOrderService.getUSerBonuses(token));
    }

    @GetMapping("/is-registered")
    public ResponseEntity<Optional<UserOrderDto>> isUserRegistered(@RequestHeader("Authorization") String  token){
        log.info("token:: " + token);
        Optional<UserOrderDto> user = userOrderService.getUserIdIfPresent(token);

        return ResponseEntity.ok(user);
    }


    @GetMapping("/profile")
    public ResponseEntity<UserProfilePage> getUserProfile(

            HttpServletRequest httpServletRequest,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue  = "2") Integer size
    ){

        Pageable pageableToPass = PageRequest.of(page, size);

        return ResponseEntity.ok(userPageService.getUserProfile(httpServletRequest, pageableToPass));
    }

}
