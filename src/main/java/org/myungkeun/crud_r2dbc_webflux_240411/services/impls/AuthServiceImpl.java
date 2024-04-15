package org.myungkeun.crud_r2dbc_webflux_240411.services.impls;

import lombok.AllArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.loginDto.LoginRequestDto;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.registerDto.RegisterRequestDto;
import org.myungkeun.crud_r2dbc_webflux_240411.config.jwt.JwtTokenUtil;
import org.myungkeun.crud_r2dbc_webflux_240411.entities.Role;
import org.myungkeun.crud_r2dbc_webflux_240411.entities.User;
import org.myungkeun.crud_r2dbc_webflux_240411.repositories.UserRepository;
import org.myungkeun.crud_r2dbc_webflux_240411.services.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> register(RegisterRequestDto request) {
        return userRepository.findByEmail(request.getEmail())
                .flatMap(user -> Mono.<User>error(new RuntimeException("Email already registered")))
                .switchIfEmpty(saveNewUser(request));
    }

    @Override
    public Mono<String> login(LoginRequestDto request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(jwtTokenUtil::generateAccessToken)
                .switchIfEmpty(Mono.error(new RuntimeException("Login failed - not found email or password")));
    }

    private Mono<User> saveNewUser(RegisterRequestDto request) {
        return Mono.fromCallable(() -> User.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .phone(request.getPhone())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .roles(Role.ROLE_USER.name())
                        .enabled(true)
                        .createdBy(request.getUsername())
                        .createdAt(LocalDateTime.now())
                        .build())
                .flatMap(userRepository::save);
    }
}
