package com.codingshuttle.ecommerce.api_gateway.filter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class GlobalLoggingFilter implements GlobalFilter {
    private static final Logger log = Logger.getLogger(GlobalLoggingFilter.class.getName());
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info(String.format("logging free Global: %s", exchange.getRequest().getURI()));
        return chain.filter(exchange);
    }
}
