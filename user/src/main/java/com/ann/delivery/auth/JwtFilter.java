package com.ann.delivery.auth;

import com.ann.delivery.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String tokenHeader = request.getHeader("Authorization");

        final String accessToken;

        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        accessToken = tokenHeader.substring(7);
        try {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = jwtService.extractUsername(accessToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                log.info("USERNAME ::" + username);
                setAuthenticationContext(userDetails, request, accessToken);
                request.setAttribute("user", userDetails);
            }
            filterChain.doFilter(request, response);

        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired");

        }

    }


    private void setAuthenticationContext(UserDetails userDetails, HttpServletRequest request, String jwtToken) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                jwtToken,
                userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }
}
