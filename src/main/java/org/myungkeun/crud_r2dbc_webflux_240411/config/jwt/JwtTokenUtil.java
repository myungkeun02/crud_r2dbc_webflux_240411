package org.myungkeun.crud_r2dbc_webflux_240411.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.myungkeun.crud_r2dbc_webflux_240411.entities.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor

public class JwtTokenUtil {
    private final JwtKeyUtil jwtKeyUtil;

    @Value("${application.security.jwt.expriration}")
    private Long jwtExpiryTime;

    @Value("${application.security.jwt.refresh-time}")
    private Long jwtRefreshTime;

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        return buildToken(claims, user, jwtExpiryTime);
    }

    public String generateRefreshToken(User user) {
        return buildToken(new HashMap<>(), user, jwtRefreshTime);
    }

    private String buildToken(Map<String, Object> extraClaims, User user, Long jwtExpiryTime) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiryTime))
                .signWith(jwtKeyUtil.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }
}
