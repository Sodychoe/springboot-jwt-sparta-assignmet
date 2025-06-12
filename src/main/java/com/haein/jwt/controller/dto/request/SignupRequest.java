package com.haein.jwt.controller.dto.request;

import com.haein.jwt.service.dto.request.SignupRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignupRequest(
    @Schema(description = "서비스에 사용할 사용자 이름", example = "test1")
    String username,
    @Schema(description = "서비스에 사용할 사용자 비밀번호", example = "test1234")
    String password,
    @Schema(description = "사용자 별명", example = "nickname")
    String nickname
) {

  public SignupRequestDto toApplicationDto(SignupRequest request) {
    return SignupRequestDto.from(request);
  }
}
