package com.example.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final Key key;
    private final long expirationTime;

    //내가 생성한 키와 만료 기한 가져오기
    public JwtProvider(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.expiration-time}") long expirationTime) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expirationTime;
    }

    //key, 발급시간, 만료 시간, loginId를 활용한 토큰 생성
    public String generateToken(String loginId) {
        Date now = new Date();
        Date validityDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(validityDate)
                .signWith(key, SignatureAlgorithm.HS256) // 내가 만든 키를 활용한 토큰 생성 함수
                .setSubject(loginId)
                .compact();

    }

    //토큰 유효성 검증
    public boolean verifyToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //토큰에서 로그인아이디 뽑아내기 (누구인지 확인할 때 사용)
    public String getLoginId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
