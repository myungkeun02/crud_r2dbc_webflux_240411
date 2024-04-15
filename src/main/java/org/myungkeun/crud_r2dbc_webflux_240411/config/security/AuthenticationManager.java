package org.myungkeun.crud_r2dbc_webflux_240411.config.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_240411.config.jwt.JwtValidationUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component

public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtValidationUtil jwtValidationUtil;

    public AuthenticationManager(JwtValidationUtil jwtValidationUtil) {
        this.jwtValidationUtil = jwtValidationUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return null;
    }

    private Mono<Authentication> authenticationEmailAndToken(String email, String token) {
        return Mono.just(jwtValidationUtil.getClaimsToken(token))
                .map(claims -> {
                    List<String> roleList = claims.get("roles", List.class);
                    return new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            roleList.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())
                    );
                });
    }
}
