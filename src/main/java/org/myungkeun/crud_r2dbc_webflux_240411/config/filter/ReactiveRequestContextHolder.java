package org.myungkeun.crud_r2dbc_webflux_240411.config.filter;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

public class ReactiveRequestContextHolder {
    private static final String BEARER = "Bearer ";
    public static Mono<String> getTokenAuth() {
        return Mono.deferContextual(Mono::just)
                .filter(contextView -> Objects.nonNull(contextView.get(ServerWebExchange.class).getRequest()))
                .map(contextView -> {
                    HttpHeaders headers = contextView.get(ServerWebExchange.class).getRequest().getHeaders();
                    return Optional.ofNullable(headers.getFirst(ContextKey.AUTHORIZATION))
                            .filter(authHeader -> authHeader.startsWith(BEARER))
                            .map(authHeaderBearer -> authHeaderBearer.substring(BEARER.length()))
                            .get();
                });
    }
}
