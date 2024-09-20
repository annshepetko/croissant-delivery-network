package com.delivery.gateway.filters;

import com.delivery.gateway.openfeign.client.AdminRoleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
public class AdminRoleFilter extends AbstractGatewayFilterFactory<AdminRoleFilter.Config> {

    private final AdminRoleClient adminRoleClient;
    private static final Logger logger = LoggerFactory.getLogger(AdminRoleFilter.class);

    @Autowired
    public AdminRoleFilter( @Lazy AdminRoleClient adminRoleClient) {
        super(Config.class);
        this.adminRoleClient = adminRoleClient;
    }



    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {

            logger.info("LOGGER HAS BEAN INITIALIZING");
            String token = null;

            List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

            if (authHeaders != null && !authHeaders.isEmpty()) {
                token = authHeaders.get(0);
                System.out.println(token);
            }
            boolean isAdmin = false;
            if (token != null && token.startsWith("Bearer ")) {


                isAdmin = adminRoleClient.isUserAdmin(token);
                logger.info("STATE: " + isAdmin);
            }

            if (!isAdmin) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);

                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                String message = "{\"error\": \"Access denied. Admin role required.\"}";

                DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();

                DataBuffer dataBuffer = dataBufferFactory.wrap(message.getBytes());

                return exchange.getResponse().writeWith(Mono.just(dataBuffer));

            }
            return chain.filter(exchange);

        },0);
    }



    public static class Config {
    }

}
