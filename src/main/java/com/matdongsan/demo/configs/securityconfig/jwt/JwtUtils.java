package com.matdongsan.demo.configs.securityconfig.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    private final SecretKey secretKey;
    private final long tokenValidTime;

    public JwtUtils(@Value("${jwt.secretKey}") String secretKey,
                    @Value("${jwt.tokenValidTime}") long tokenValidTime) {

        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.tokenValidTime = tokenValidTime;
    }

    public String createToken(String username, String role) {

        Date createTime = new Date();
        Date expireTime = new Date(createTime.getTime() + this.tokenValidTime);

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(createTime)
                .expiration(expireTime)
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
}
