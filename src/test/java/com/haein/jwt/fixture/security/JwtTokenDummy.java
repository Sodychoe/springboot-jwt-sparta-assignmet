package com.haein.jwt.fixture.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:application-security-test.yml")
public class JwtTokenDummy {

  private static final String BEARER_PREFIX = "Bearer ";
  private static String secretKey;

  public static void setSecretKey(String secretKey) {
    JwtTokenDummy.secretKey = secretKey;
  }

  public static List<String> testTokenFactory() {
    return List.of(
        tokenBuilder(secretKey.substring(1), new Date(System.currentTimeMillis() + 300000)),
        tokenBuilder(secretKey, new Date(System.currentTimeMillis() + 300000)).replace(".", ""),
        tokenBuilder(secretKey, new Date(System.currentTimeMillis())),
        "Bearer " // 빈 토큰
    );
  }

  private static String tokenBuilder(String secretKey, Date expiration) {
    Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));

    String token = Jwts.builder()
        .setSubject("test1234")
        .claim("role", "test")
        .setExpiration(expiration)
        .setIssuedAt(new Date())
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    return BEARER_PREFIX + token;
  }
}
