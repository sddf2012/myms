package com.my.base.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author liu peng bo
 * @date 2022/4/5 下午5:47
 */
@Configuration
public class GatewayConfig {

    @Bean
    public KeyResolver apiResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
    }

}
