package org.myungkeun.crud_r2dbc_webflux_240411.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

@Component

public class JwtValidationUtil {
    private final JwtKeyUtil jwtKeyUtil;

    public JwtValidationUtil(JwtKeyUtil jwtKeyUtil) {
        this.jwtKeyUtil = jwtKeyUtil;
    }

    @Value("${application.security.jwt.secretkey}")
    private String secretKey;

    public Claims getClaimsToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtKeyUtil.getPrivateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmail(String token) {
        return getClaimsToken(token).getSubject();
    }

    public Date getExpiryTime(String token) {
        return getClaimsToken(token).getExpiration();
    }

    public Boolean isExpired(String token) {
        return getExpiryTime(token).before(new Date());
    }

    public Boolean isTokenValid(String token, String requestUsername) {
        final String username = getEmail(token);
        return (username.equals(requestUsername) && !isExpired(token));
    }
}
