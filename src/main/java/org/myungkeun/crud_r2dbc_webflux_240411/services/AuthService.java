package org.myungkeun.crud_r2dbc_webflux_240411.services;

import org.myungkeun.crud_r2dbc_webflux_240411.Dto.loginDto.LoginRequestDto;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.registerDto.RegisterRequestDto;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.registerDto.RegisterResponseDto;
import org.myungkeun.crud_r2dbc_webflux_240411.entities.User;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<User> register(RegisterRequestDto request);

    Mono<String> login(LoginRequestDto request);
}
