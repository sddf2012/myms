package com.my.base.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.HasRouteId;
import org.springframework.cloud.gateway.support.HttpStatusHolder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;

@Component
public class CustomRequestRateLimiterGatewayFilterFactory extends
        AbstractGatewayFilterFactory<CustomRequestRateLimiterGatewayFilterFactory.Config> {

    /**
     * Key-Resolver key.
     */
    public static final String KEY_RESOLVER_KEY = "keyResolver";

    private static final String EMPTY_KEY = "____EMPTY_KEY__";

    private final RateLimiter defaultRateLimiter;

    private final KeyResolver defaultKeyResolver;

    /**
     * Switch to deny requests if the Key Resolver returns an empty key, defaults to true.
     */
    private boolean denyEmptyKey = true;

    /**
     * HttpStatus to return when denyEmptyKey is true, defaults to FORBIDDEN.
     */
    private String emptyKeyStatusCode = HttpStatus.FORBIDDEN.name();

    public CustomRequestRateLimiterGatewayFilterFactory(RateLimiter defaultRateLimiter,
                                                        KeyResolver defaultKeyResolver) {
        super(CustomRequestRateLimiterGatewayFilterFactory.Config.class);
        this.defaultRateLimiter = defaultRateLimiter;
        this.defaultKeyResolver = defaultKeyResolver;
    }

    public KeyResolver getDefaultKeyResolver() {
        return defaultKeyResolver;
    }

    public RateLimiter getDefaultRateLimiter() {
        return defaultRateLimiter;
    }

    public boolean isDenyEmptyKey() {
        return denyEmptyKey;
    }

    public void setDenyEmptyKey(boolean denyEmptyKey) {
        this.denyEmptyKey = denyEmptyKey;
    }

    public String getEmptyKeyStatusCode() {
        return emptyKeyStatusCode;
    }

    public void setEmptyKeyStatusCode(String emptyKeyStatusCode) {
        this.emptyKeyStatusCode = emptyKeyStatusCode;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GatewayFilter apply(CustomRequestRateLimiterGatewayFilterFactory.Config config) {
        KeyResolver resolver = getOrDefault(config.keyResolver, defaultKeyResolver);
        RateLimiter<Object> limiter = getOrDefault(config.rateLimiter,
                defaultRateLimiter);
        boolean denyEmpty = getOrDefault(config.denyEmptyKey, this.denyEmptyKey);
        HttpStatusHolder emptyKeyStatus = HttpStatusHolder
                .parse(getOrDefault(config.emptyKeyStatus, this.emptyKeyStatusCode));

        return (exchange, chain) -> resolver.resolve(exchange).defaultIfEmpty(EMPTY_KEY)
                .flatMap(key -> {
                    if (EMPTY_KEY.equals(key)) {
                        if (denyEmpty) {
                            setResponseStatus(exchange, emptyKeyStatus);
                            return exchange.getResponse().setComplete();
                        }
                        return chain.filter(exchange);
                    }
                    String routeId = config.getRouteId();
                    if (routeId == null) {
                        Route route = exchange
                                .getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                        routeId = route.getId();
                    }
                    return limiter.isAllowed(routeId, key).flatMap(response -> {

                        for (Map.Entry<String, String> header : response.getHeaders()
                                .entrySet()) {
                            exchange.getResponse().getHeaders().add(header.getKey(),
                                    header.getValue());
                        }

                        if (response.isAllowed()) {
                            return chain.filter(exchange);
                        }

                        setResponseStatus(exchange, config.getStatusCode());
                        ServerHttpResponse httpResponse = exchange.getResponse();
                        DataBuffer dataBuffer = httpResponse.bufferFactory().wrap("too many request!".getBytes(StandardCharsets.UTF_8));
                        return httpResponse.writeWith(Mono.just(dataBuffer));
                    });
                });
    }

    private <T> T getOrDefault(T configValue, T defaultValue) {
        return (configValue != null) ? configValue : defaultValue;
    }

    public static class Config implements HasRouteId {

        private KeyResolver keyResolver;

        private RateLimiter rateLimiter;

        private HttpStatus statusCode = HttpStatus.TOO_MANY_REQUESTS;

        private Boolean denyEmptyKey;

        private String emptyKeyStatus;

        private String routeId;

        public KeyResolver getKeyResolver() {
            return keyResolver;
        }

        public CustomRequestRateLimiterGatewayFilterFactory.Config setKeyResolver(KeyResolver keyResolver) {
            this.keyResolver = keyResolver;
            return this;
        }

        public RateLimiter getRateLimiter() {
            return rateLimiter;
        }

        public CustomRequestRateLimiterGatewayFilterFactory.Config setRateLimiter(RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
            return this;
        }

        public HttpStatus getStatusCode() {
            return statusCode;
        }

        public CustomRequestRateLimiterGatewayFilterFactory.Config setStatusCode(HttpStatus statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Boolean getDenyEmptyKey() {
            return denyEmptyKey;
        }

        public CustomRequestRateLimiterGatewayFilterFactory.Config setDenyEmptyKey(Boolean denyEmptyKey) {
            this.denyEmptyKey = denyEmptyKey;
            return this;
        }

        public String getEmptyKeyStatus() {
            return emptyKeyStatus;
        }

        public CustomRequestRateLimiterGatewayFilterFactory.Config setEmptyKeyStatus(String emptyKeyStatus) {
            this.emptyKeyStatus = emptyKeyStatus;
            return this;
        }

        @Override
        public void setRouteId(String routeId) {
            this.routeId = routeId;
        }

        @Override
        public String getRouteId() {
            return this.routeId;
        }

    }

}
