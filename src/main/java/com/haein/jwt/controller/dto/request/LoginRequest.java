package com.haein.jwt.controller.dto.request;

import com.haein.jwt.service.dto.request.LoginRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
    @Schema(description = "사용자 아이디", example = "test1")
    String username,
    @Schema(description = "비밀번호", example = "test1234")
    String password
) {

  public LoginRequestDto toApplicationDto(LoginRequest request) {
    return new LoginRequestDto(request.username(), request.password());
  }
}
