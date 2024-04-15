package org.myungkeun.crud_r2dbc_webflux_240411.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component

public class SecurityContextRepository implements ServerSecurityContextRepository {
    private static final String BEARER = "Bearer ";
    private final AuthenticationManager authenticationManager;

    public SecurityContextRepository(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return null;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(BEARER))
                .flatMap(this::authenticateUserToken);
    }

    private Mono<SecurityContextImpl> authenticateUserToken(String authHeaderBearer) {
        String authToken = authHeaderBearer.substring(BEARER.length());
        Authentication authentication = new UsernamePasswordAuthenticationToken(authToken, authToken);
        return authenticationManager
                .authenticate(authentication)
                .map(SecurityContextImpl::new);
    }
}
