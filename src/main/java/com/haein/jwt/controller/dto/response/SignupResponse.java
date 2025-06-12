package com.haein.jwt.controller.dto.response;

import com.haein.jwt.service.dto.response.SignupResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignupResponse(
    @Schema(description = "사용자 id", example = "1")
    Long id,
    @Schema(description = "사용자 이름", example = "test1")
    String username,
    @Schema(description = "서비스에 사용할 사용자 비밀번호", example = "test1234")
    String password,
    @Schema(description = "사용자 별명", example = "nickname")
    String nickname
) {
  public static SignupResponse from(SignupResponseDto dto) {
    return new SignupResponse(dto.id(), dto.username(), dto.password(), dto.nickname());
  }
}
