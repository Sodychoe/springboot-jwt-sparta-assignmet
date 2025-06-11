package com.haein.jwt.fixture.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class JwtTokenDummy {

  private static final String BEARER_PREFIX = "Bearer ";
  private static final String secretKey = "c902007a118cbc622f60b79d2571472c5bd8109998016b8d66bce46fb68a83c4291071c653ca5a30ef65c76904136a66657e381d625eb17988b5a126f9480339";


  private JwtTokenDummy() {

  }

  public static List<String> testTokenFactory() {
    return List.of(
        "prefix가없는토큰예시",
        tokenBuilder(secretKey.substring(1), new Date(System.currentTimeMillis() + 300000)),
        tokenBuilder(secretKey, new Date(System.currentTimeMillis() + 300000)).replace(".", ""),
        tokenBuilder(secretKey, new Date(System.currentTimeMillis() + 3)),
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
