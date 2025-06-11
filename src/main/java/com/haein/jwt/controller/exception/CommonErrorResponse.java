package com.haein.jwt.controller.exception;

import com.haein.jwt.security.SecurityErrorCode;
import com.haein.jwt.service.exception.ServiceErrorCode;

public record CommonErrorResponse(Error error) {

  public static final String AUTHENTICATION_FAIL_MESSAGE = "인증에 실패하여 해당 서비스에 접근할 수 없습니다. 관리자에게 문의 바랍니다.";

  public static CommonErrorResponse of(ServiceErrorCode serviceErrorCode) {
    return new CommonErrorResponse(
        new Error(serviceErrorCode.getCode(), serviceErrorCode.getMessage()));
  }

  public static CommonErrorResponse of(SecurityErrorCode securityErrorCode) {
    return new CommonErrorResponse(
        new Error(securityErrorCode.getCode(), AUTHENTICATION_FAIL_MESSAGE));
  }

  private record Error(
      String code,
      String message
  ) {

  }

}
