package com.haein.jwt.controller.exception;

import com.haein.jwt.security.SecurityErrorCode;
import com.haein.jwt.service.exception.ServiceErrorCode;

public record CommonErrorResponse(Error error) {

  public static CommonErrorResponse of(ServiceErrorCode serviceErrorCode) {
    return new CommonErrorResponse(
        new Error(serviceErrorCode.getCode(), serviceErrorCode.getMessage()));
  }

  public static CommonErrorResponse of(SecurityErrorCode securityErrorCode) {
    return new CommonErrorResponse(
        new Error(securityErrorCode.getCode(), securityErrorCode.getMessage()));
  }

  private record Error(
      String code,
      String message
  ) {

  }

}
