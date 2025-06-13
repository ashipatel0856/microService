package com.codingshuttle.ecommerce.api_gateway.filter;

import com.codingshuttle.ecommerce.api_gateway.service.JwtService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationGatewayFilterFactory.class);
    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }
    

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) ->{
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if(authorizationHeader == null){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
//            String token = authorizationHeader.substring(7);
            String token = authorizationHeader.split("Bearer ")[1];

            Long userId = jwtService.getUserIdFromToken(token);

            exchange.getRequest()
                    .mutate()
                    .header("X-USER-ID", userId.toString())
                    .build();
            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config {
        private boolean isEnabled;
    }
}
