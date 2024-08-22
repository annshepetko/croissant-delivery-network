package com.ann.delivery.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public enum Roles {
    CUSTOMER,
    ADMIN;


    public List<SimpleGrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}
