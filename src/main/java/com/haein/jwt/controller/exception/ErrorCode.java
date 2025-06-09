package com.haein.jwt.controller.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
  USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "이미 가입된 사용자입니다.", HttpStatus.CONFLICT.value());

  private final String code;
  private final String message;
  private final int status;

  ErrorCode(String code, String message, int status) {
    this.code = code;
    this.message = message;
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public int getStatus() {
    return status;
  }
}
