package com.ann.delivery.auth;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@TestConfiguration
public class TestConfig {



    @Bean
    public DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception {
       return http
               .csrf().disable()
                .authorizeRequests().anyRequest().permitAll().and().build();
    }
}