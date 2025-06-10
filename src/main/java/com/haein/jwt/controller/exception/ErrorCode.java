package com.haein.jwt.controller.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "이미 가입된 사용자입니다.", HttpStatus.CONFLICT.value()),
  INVALID_CREDENTIALS("INVALID_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다." , HttpStatus.UNAUTHORIZED.value() );

  private final String code;
  private final String message;
  private final int status;

  ErrorCode(String code, String message, int status) {
    this.code = code;
    this.message = message;
    this.status = status;
  }

}
