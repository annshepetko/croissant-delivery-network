package com.ann.delivery.controller;

import com.ann.delivery.dto.order.UserOrderDto;
import com.ann.delivery.dto.profile.UserProfilePage;
import com.ann.delivery.entity.User;
import com.ann.delivery.services.UserOrderService;
import com.ann.delivery.services.UserPageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<Double> getUserBonuses(HttpServletRequest request) {
        log.info("Request to get user bonuses received");

        User user = (User) request.getAttribute("user");
        double bonuses = userOrderService.getUserBonuses(user);

        log.info("Bonuses for user {}: {}", user.getEmail(), bonuses);
        return ResponseEntity.ok(bonuses);
    }

    @GetMapping("/is-registered")
    public ResponseEntity<Optional<UserOrderDto>> isUserRegistered(HttpServletRequest request) {
        log.info("Checking if user is registered...");

        String username = (String) request.getAttribute("username");
        Optional<UserOrderDto> userOrderDto = userOrderService.getUserOrderIfPresent(username);

        if (userOrderDto.isPresent()) {
            log.info("User {} is registered", username);
        } else {
            log.warn("User {} is not registered", username);
        }

        return ResponseEntity.ok(userOrderDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfilePage> getUserProfile(
            HttpServletRequest request,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "2") Integer size) {

        log.debug("Request to get user profile page {} with size {}", page, size);
        User user = (User) request.getAttribute("user");

        Pageable pageable = PageRequest.of(page, size);
        UserProfilePage profilePage = userPageService.getUserProfile(user, pageable);

        log.debug("Profile page retrieved for user: {}", request.getAttribute("username"));
        return ResponseEntity.ok(profilePage);
    }
}
