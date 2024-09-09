package com.ann.delivery.controller;


import com.ann.delivery.dto.UserDto;
import com.ann.delivery.dto.UserProfilePage;
import com.ann.delivery.entity.User;
import com.ann.delivery.services.UserOrderService;
import com.ann.delivery.services.UserPageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserOrderService userOrderService;
    private final UserPageService userPageService;

    @GetMapping("/bonuses")
    public ResponseEntity<Double> getUserBonuses(@RequestHeader(HttpHeaders.AUTHORIZATION) String token ){
        return ResponseEntity.ok(userOrderService.getUSerBonuses(token));
    }

    @GetMapping("/is-registered")
    public ResponseEntity<Optional<UserDto>> isUserRegistered(@RequestHeader("Authorization") String  token){
        log.info("token:: " + token);
        Optional<UserDto> user = userOrderService.getUserIdIfPresent(token);

        return ResponseEntity.ok(user);
    }


    @GetMapping("/profile")
    public ResponseEntity<UserProfilePage> getUserProfile(HttpServletRequest httpServletRequest){

        return ResponseEntity.ok(userPageService.getUserProfile(httpServletRequest));
    }

}
