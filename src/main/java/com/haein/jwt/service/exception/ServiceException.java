package com.haein.jwt.service.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

  private final ServiceErrorCode serviceErrorCode;
  private final int status;

  private ServiceException(ServiceErrorCode serviceErrorCode, int status) {
    super(serviceErrorCode.getMessage());
    this.serviceErrorCode = serviceErrorCode;
    this.status = status;
  }

  public static ServiceException from(ServiceErrorCode serviceErrorCode) {
    return new ServiceException(serviceErrorCode, serviceErrorCode.getStatus());
  }

}
