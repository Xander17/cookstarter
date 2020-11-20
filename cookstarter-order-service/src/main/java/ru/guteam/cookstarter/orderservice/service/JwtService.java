package ru.guteam.cookstarter.orderservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${app.jwt.secret-key}")
    private String secretKey;
    @Value("${app.jwt.inner-token-lifetime:60}")
    private int innerTokenLifetime;

    public void checkToken(String token) {
        Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
    }

    public String generateInnerToken() {
        Date time = new Date();
        return Jwts.builder()
                .setIssuedAt(time)
                .setExpiration(new Date(time.getTime() + innerTokenLifetime * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
