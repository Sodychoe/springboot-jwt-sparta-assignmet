package com.haein.jwt.controller.dto.response;

import com.haein.jwt.service.dto.response.LoginResponseDto;

public record LoginResponse(
    String token
) {

  public static LoginResponse from(LoginResponseDto dto) {
    return new LoginResponse(dto.token());
  }
}
