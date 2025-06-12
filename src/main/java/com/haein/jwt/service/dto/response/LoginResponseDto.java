package com.haein.jwt.service.dto.response;

public record LoginResponseDto(
    String token
) {
  public static LoginResponseDto from(String token) {
    return new LoginResponseDto(token);
  }
}
