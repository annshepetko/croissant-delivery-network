package com.ann.delivery.controller;


import com.ann.delivery.services.AdminService;
import com.ann.delivery.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin/")
public class AdminController {

    public final AdminService adminService;

    @GetMapping("/is-admin")
    public Boolean isAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, HttpServletRequest request){

        String username = (String) request.getAttribute("username");

        Boolean state = adminService.isUserAdmin(username);
        return state;
    }
}
