package com.ann.delivery.auth;

import com.ann.delivery.enums.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityFilterChain {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity security) throws Exception {
        return security
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling((exc) -> {
                    exc.authenticationEntryPoint(authenticationEntryPoint);
                })
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/api/say").hasRole(Roles.CUSTOMER.name())
                                .anyRequest()
                                .authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .build();
    }

}
