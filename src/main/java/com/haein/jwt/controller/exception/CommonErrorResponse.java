package com.haein.jwt.controller.exception;

import com.haein.jwt.service.exception.ErrorCode;

public record CommonErrorResponse(Error error) {

  public static CommonErrorResponse of(ErrorCode errorCode) {
    return new CommonErrorResponse(new Error(errorCode.getCode(), errorCode.getMessage()));
  }

  private record Error(
      String code,
      String message
  ) {

  }

}
