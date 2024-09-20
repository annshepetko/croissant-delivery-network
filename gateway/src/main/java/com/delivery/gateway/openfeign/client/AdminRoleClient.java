package com.delivery.gateway.openfeign.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.Optional;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:8080")
public interface AdminRoleClient {

    @GetMapping("/api/admin/is-admin")
    Boolean isUserAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
