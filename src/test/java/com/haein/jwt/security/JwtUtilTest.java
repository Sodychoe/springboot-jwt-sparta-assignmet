package com.haein.jwt.security;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.haein.jwt.fixture.security.JwtTokenDummy;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("security-test")
@TestPropertySource(locations = "classpath:application-security-test.yml")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = JwtUtil.class)
class JwtUtilTest {

  @Autowired
  JwtUtil jwtUtil;

  @Autowired
  Environment environment;

  private static Stream<Arguments> invalidTokens() {
    List<String> tokens = JwtTokenDummy.testTokenFactory();
    return Stream.of(
        Arguments.arguments(tokens.get(0), SecurityErrorCode.INVALID_SIGNATURE.getMessage()),
        Arguments.arguments(tokens.get(1), SecurityErrorCode.INVALID_SIGNATURE.getMessage()),
        Arguments.arguments(tokens.get(2), SecurityErrorCode.ACCESS_TOKEN_EXPIRED.getMessage()),
        Arguments.arguments(tokens.get(3), SecurityErrorCode.EMPTY_TOKEN.getMessage())
    );
  }

  @BeforeEach
  void init() {
    JwtTokenDummy.setSecretKey(environment.getProperty("service.jwt.secret-key"));
  }

  @Test
  @DisplayName("Bearer prefix 없는 입력이 들어오면 커스텀 JwtException 을 던진다")
  void givenTokenWithoutPrefix_whenParseToken_thenThrowJwtException() {
    // given
    String missingPrefixToken = "sadkjassjfdbasfbajsfhasjh";

    // when & then
    assertThatThrownBy(() -> jwtUtil.parseToken(missingPrefixToken))
        .isInstanceOf(JwtException.class)
        .hasMessageContaining(SecurityErrorCode.INVALID_PREFIX.getMessage());
  }

  @ParameterizedTest
  @MethodSource("invalidTokens")
  @DisplayName("토큰 검증에서 무결성을 만족하지 못하면 토큰 상태에 따라 적절한 커스텀 JwtException 을 던진다")
  void givenInvalidToken_whenValidate_thenThrowJwtException(String token, String message) {
    assertThatThrownBy(() -> jwtUtil.validateToken(token))
        .isInstanceOf(JwtException.class)
        .hasMessageContaining(message);
  }
}
