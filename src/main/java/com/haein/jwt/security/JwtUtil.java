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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
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
      throw JwtException.from(SecurityErrorCode.INVALID_SIGNATURE);
    } catch (ExpiredJwtException e) {
      throw JwtException.from(SecurityErrorCode.ACCESS_TOKEN_EXPIRED);
    } catch (UnsupportedJwtException e) {
      throw JwtException.from(SecurityErrorCode.UNSUPPORTED_TOKEN);
    } catch (IllegalArgumentException e) {
      throw JwtException.from(SecurityErrorCode.EMPTY_TOKEN);
    }
  }

  private String removeTokenPrefix(String token) {
    if (token.startsWith(BEARER_PREFIX)) {
      return token.substring(BEARER_PREFIX.length());
    }

    throw new RuntimeException();
  }
}
