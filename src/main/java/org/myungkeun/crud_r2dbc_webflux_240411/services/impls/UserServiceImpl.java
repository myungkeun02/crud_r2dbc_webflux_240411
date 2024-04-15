package org.myungkeun.crud_r2dbc_webflux_240411.services.impls;

import lombok.RequiredArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_240411.config.jwt.JwtValidationUtil;
import org.myungkeun.crud_r2dbc_webflux_240411.entities.User;
import org.myungkeun.crud_r2dbc_webflux_240411.repositories.UserRepository;
import org.myungkeun.crud_r2dbc_webflux_240411.services.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtValidationUtil jwtValidationUtil;

    @Override
    public Mono<String> getEmail(String token) {
        return Mono.just(jwtValidationUtil.getEmail(token))
                .flatMap(userRepository::findByEmail)
                .filter(Objects::nonNull)
                .map(User::getEmail)
                .switchIfEmpty(Mono.<String>error(new RuntimeException("error when get email = not found email")));
    }
}
