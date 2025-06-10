package com.haein.jwt.controller.dto.request;

import com.haein.jwt.service.dto.request.LoginRequestDto;

public record LoginRequest(
        String username,
        String password
) {

  public LoginRequestDto toApplicationDto(LoginRequest request) {
    return new LoginRequestDto(request.username(), request.password());
  }
}
