package com.ann.delivery.controller;


import com.amazonaws.Response;
import com.ann.delivery.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/is-registered")
    public ResponseEntity<Optional<Long>> isUserRegistered(@RequestHeader("Authorization") String  token){
        log.info("token:: " + token);
        Optional<Long> id = userService.getUserIdIfPresent(token);

        return ResponseEntity.ok(id);
    }

}
