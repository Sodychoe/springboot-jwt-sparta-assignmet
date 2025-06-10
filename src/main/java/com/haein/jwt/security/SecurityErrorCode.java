package com.haein.jwt.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode {
  INVALID_SIGNATURE("INVALID_SIGNATURE", "올바르지 않은 서명입니다."),
  ACCESS_TOKEN_EXPIRED("ACCESS_TOKEN_EXPIRED", "액세스 토큰이 만료되었습니다."),
  REFRESH_TOKEN_EXPIRED("REFRESH_TOKEN_EXPIRED", "리프레시 토큰이 만료되었습니다."),
  UNSUPPORTED_TOKEN("UNSUPPORTED_TOKEN", "지원하지 않는 JWT 토큰입니다."),
  EMPTY_TOKEN("EMPTY_TOKEN", "토큰이 비어있습니다.");


  private final String code;
  private final String message;
  private final int status = HttpStatus.UNAUTHORIZED.value();

}
