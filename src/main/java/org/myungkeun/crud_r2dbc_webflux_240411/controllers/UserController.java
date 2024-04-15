package org.myungkeun.crud_r2dbc_webflux_240411.controllers;

import lombok.RequiredArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.BaseResponseDto;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.UserInfo.UserInfoResponse;
import org.myungkeun.crud_r2dbc_webflux_240411.config.filter.ReactiveRequestContextHolder;
import org.myungkeun.crud_r2dbc_webflux_240411.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public Mono<ResponseEntity<BaseResponseDto<UserInfoResponse>>> getUserByToken() {
        return ReactiveRequestContextHolder.getTokenAuth()
                .flatMap(userService::getEmail)
                .map(userEmail -> ResponseEntity.ok(BaseResponseDto.<UserInfoResponse>builder()
                        .statusCode(200)
                        .message("get user success")
                        .data(new UserInfoResponse(userEmail))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(BaseResponseDto.<UserInfoResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(null)
                                .build())));
    }
}
