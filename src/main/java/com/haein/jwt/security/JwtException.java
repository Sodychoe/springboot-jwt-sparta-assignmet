package com.haein.jwt.security;

import lombok.Getter;

@Getter
public class JwtException extends RuntimeException {

  private final SecurityErrorCode code;
  private final int status;

  public static JwtException from(SecurityErrorCode code) {
    return new JwtException(code);
  }

  private JwtException(SecurityErrorCode code) {
    super(code.getMessage());
    this.code = code;
    this.status = code.getStatus();
  }
}
