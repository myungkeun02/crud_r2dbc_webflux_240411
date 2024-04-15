package org.myungkeun.crud_r2dbc_webflux_240411.services;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<String> getEmail(String token);
}
