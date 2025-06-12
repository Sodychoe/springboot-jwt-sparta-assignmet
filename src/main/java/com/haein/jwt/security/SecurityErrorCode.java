package com.haein.jwt.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode {
  INVALID_SIGNATURE("INVALID_TOKEN", "토큰이 손상되었습니다."),
  ACCESS_TOKEN_EXPIRED("ACCESS_TOKEN_EXPIRED", "액세스 토큰이 만료되었습니다."),
  REFRESH_TOKEN_EXPIRED("REFRESH_TOKEN_EXPIRED", "리프레시 토큰이 만료되었습니다."),
  UNSUPPORTED_TOKEN("UNSUPPORTED_TOKEN", "지원하지 않는 JWT 토큰입니다."),
  EMPTY_TOKEN("EMPTY_TOKEN", "토큰이 비어있습니다."),
  MISSING_HEADER("MISSING_HEADER", "Authorization 헤더가 없습니다."),
  INVALID_PREFIX("INVALID_FORMAT", "Bearer Prefix 를 찾지 못해 파싱에 실패했습니다."),
  NO_AUTHORIZATION("NO_AUTHORIZATION", "주어진 권한으로 접근할 수 없는 서비스입니다.");


  private final String code;
  private final String message;
  private final int status = HttpStatus.UNAUTHORIZED.value();

  @Override
  public String toString() {
    return "SecurityErrorCode{" +
        "code='" + code + '\'' +
        ", message='" + message + '\'' +
        ", status=" + status +
        '}';
  }
}
