package com.haein.jwt.controller.exception;

import com.haein.jwt.security.SecurityErrorCode;
import com.haein.jwt.service.exception.ServiceErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

public record CommonErrorResponse(
    @Schema(description = "에러 상세 정보")
    Error error) {

  public static final String AUTHENTICATION_FAIL_MESSAGE = "인증에 실패하여 해당 서비스에 접근할 수 없습니다. 관리자에게 문의 바랍니다.";

  public static CommonErrorResponse of(ServiceErrorCode serviceErrorCode) {
    return new CommonErrorResponse(
        new Error(serviceErrorCode.getCode(), serviceErrorCode.getMessage()));
  }

  public static CommonErrorResponse of(SecurityErrorCode securityErrorCode) {
    return new CommonErrorResponse(
        new Error(securityErrorCode.getCode(), AUTHENTICATION_FAIL_MESSAGE));
  }

  public static CommonErrorResponse securityErrorResponse() {
    return new CommonErrorResponse(
        new Error(ServiceErrorCode.NO_AUTHORIZATION.getCode(),
            ServiceErrorCode.NO_AUTHORIZATION.getMessage()));
  }

  private record Error(
      @Schema(description = "에러 코드",  example = "USER_NOT_FOUND")
      String code,
      @Schema(description = "에러 메시지", example = "존재하지 않는 사용자입니다.")
      String message
  ) {

  }

}
