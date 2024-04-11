package org.myungkeun.crud_r2dbc_webflux_240411.services.impls;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.registerDto.RegisterRequestDto;
import org.myungkeun.crud_r2dbc_webflux_240411.Dto.registerDto.RegisterResponseDto;
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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> register(RegisterRequestDto request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(Objects::nonNull)
                .flatMap(user -> Mono.<User>error(new RuntimeException("Email already registered")))
                .switchIfEmpty(saveNewUser(request));
    }

    //saveUser
    private Mono<User> saveNewUser(RegisterRequestDto request) {
        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Role.ROLE_USER.name())
                .enabled(Boolean.TRUE)
                .createdBy(request.getUsername())
                .createdAt(LocalDateTime.now())
                .build();
        return userRepository.save(newUser);
    }
}
