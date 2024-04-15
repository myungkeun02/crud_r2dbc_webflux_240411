package org.myungkeun.crud_r2dbc_webflux_240411.controllers;

import lombok.AllArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.BaseResponseDto;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.loginDto.LoginRequestDto;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.loginDto.LoginResponseDto;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.registerDto.RegisterRequestDto;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.registerDto.RegisterResponseDto;
import org.myungkeun.crud_r2dbc_webflux_240411.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")

public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public Mono<ResponseEntity<BaseResponseDto<RegisterResponseDto>>> register(@RequestBody RegisterRequestDto request) {
        return authService.register(request)
                .map(user -> ResponseEntity.ok(BaseResponseDto.<RegisterResponseDto>builder()
                        .statusCode(201)
                        .message("success")
                        .data(new RegisterResponseDto(user))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponseDto.<RegisterResponseDto>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new RegisterResponseDto(null))
                                .build()
                        )));

    }

    @PostMapping("/login")
    public Mono<ResponseEntity<BaseResponseDto<LoginResponseDto>>> login(
            @RequestBody LoginRequestDto request
    ) {
        return authService.login(request)
                .map(token -> ResponseEntity.ok(BaseResponseDto.<LoginResponseDto>builder()
                        .statusCode(200)
                        .message("success")
                        .data(new LoginResponseDto(token))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(BaseResponseDto.<LoginResponseDto>builder()
                                .statusCode(HttpStatus.FORBIDDEN.value())
                                .message(throwable.getMessage())
                                .data(new LoginResponseDto(null))
                                .build()
                        )));
    }
}
