package com.sparta.baclub.jwt;

import com.sparta.baclub.user.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_AUTHORIZATION_HEADER = "Refresh-Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct //딱 한번만 받아오면 되는 값을 사용할 때마다 새로 요청하는 것을 방지하기 위함
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public String createAccessToken(String username, UserRoleEnum role) {
        Date date = new Date();
        // access token 생성
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    public String createRefreshToken(UserRoleEnum role) {
        Date date = new Date();
        // Refresh Token 생성
        return BEARER_PREFIX +
                Jwts.builder()
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}