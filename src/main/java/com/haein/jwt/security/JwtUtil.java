package com.haein.jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {

  private static final String BEARER_PREFIX = "Bearer ";

  private final Key key;
  private final long accessExpiration;

  public JwtUtil(
      @Value("${service.jwt.secret-key}") String secretKey,
      @Value("${service.jwt.access-expiration}") long accessExpiration
  ) {
    this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    this.accessExpiration = accessExpiration;
  }

  public Claims parseToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(removeTokenPrefix(token))
        .getBody();
  }

  public String createAccessToken(String username, String role) {
    String token = Jwts.builder()
        .setSubject(username)
        .claim("role", role)
        .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
        .setIssuedAt(new Date())
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    return BEARER_PREFIX + token;
  }

  public void validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(removeTokenPrefix(token));
    } catch (SecurityException | MalformedJwtException | SignatureException e) {
      log.error("토큰 손상, cause = {}", e.toString());
      throw JwtException.from(SecurityErrorCode.INVALID_SIGNATURE);
    } catch (ExpiredJwtException e) {
      log.error("토큰 유효기간 만료, cause = {}", e.toString());
      throw JwtException.from(SecurityErrorCode.ACCESS_TOKEN_EXPIRED);
    } catch (UnsupportedJwtException e) {
      log.error("지원하지 않는 JWS 토큰, cause = {}", e.toString());
      throw JwtException.from(SecurityErrorCode.UNSUPPORTED_TOKEN);
    } catch (IllegalArgumentException e) {
      log.error("토큰의 내용이 비어 있음");
      throw JwtException.from(SecurityErrorCode.EMPTY_TOKEN);
    }
  }

  private String removeTokenPrefix(String token) {
    if (token.startsWith(BEARER_PREFIX)) {
      return token.substring(BEARER_PREFIX.length());
    }

    throw JwtException.from(SecurityErrorCode.INVALID_PREFIX);
  }
}
