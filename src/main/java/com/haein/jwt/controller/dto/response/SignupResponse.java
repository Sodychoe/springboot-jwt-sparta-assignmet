package com.haein.jwt.controller.dto.response;

import com.haein.jwt.service.dto.response.SignupResponseDto;

public record SignupResponse(
    String username,
    String password,
    String nickname
) {
  public static SignupResponse from(SignupResponseDto dto) {
    return new SignupResponse(dto.username(), dto.password(), dto.nickname());
  }
}
