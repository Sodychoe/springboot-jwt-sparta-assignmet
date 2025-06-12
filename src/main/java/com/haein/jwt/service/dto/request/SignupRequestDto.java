package com.haein.jwt.service.dto.request;

import com.haein.jwt.controller.dto.request.SignupRequest;

public record SignupRequestDto(
    String username,
    String password,
    String nickname
) {

  public static SignupRequestDto from(SignupRequest request) {
    return new SignupRequestDto(
        request.username(),
        request.password(),
        request.nickname()
    );
  }
}
