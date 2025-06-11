package com.haein.jwt.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ServiceErrorCode {
  USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "이미 가입된 사용자입니다.", HttpStatus.CONFLICT.value()),
  INVALID_CREDENTIALS("INVALID_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED.value()),
  USER_NOT_FOUND("USER_NOT_FOUND", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND.value()),
  USER_ALREADY_ADMIN("USER_ALREADY_ADMIN", "요청한 사용자는 이미 관리자입니다.",  HttpStatus.BAD_REQUEST.value());

  private final String code;
  private final String message;
  private final int status;

  ServiceErrorCode(String code, String message, int status) {
    this.code = code;
    this.message = message;
    this.status = status;
  }

}
