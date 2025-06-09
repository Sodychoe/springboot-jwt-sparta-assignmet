package com.haein.jwt.controller.dto.request;

import com.haein.jwt.service.dto.request.SignupRequestDto;

public record SignupRequest(
    String username,
    String password,
    String nickname
) {

  public SignupRequestDto toApplicationDto(SignupRequest request) {
    return SignupRequestDto.from(request);
  }
}
