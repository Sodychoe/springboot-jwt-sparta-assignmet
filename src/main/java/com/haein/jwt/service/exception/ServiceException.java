package com.haein.jwt.service.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

  private final ErrorCode errorCode;
  private final int status;

  private ServiceException(ErrorCode errorCode, int status) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
    this.status = status;
  }

  public static ServiceException from(ErrorCode errorCode) {
    return new ServiceException(errorCode, errorCode.getStatus());
  }

}
