package com.haein.jwt.controller.dto.response;

import com.haein.jwt.service.dto.response.LoginResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
    @Schema(description = "JWT 토큰", example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MSIsInJvbGUiOiJST0xFX05PUk1BTCIsImV4cCI6MTc0OTY5MjY3NiwiaWF0IjoxNzQ5NjkyMDc2fQ.S28_TZBS_2L5oJjHMTJ3eL_9JpptN_L_ab74DUtDy9k")
    String token
) {

  public static LoginResponse from(LoginResponseDto dto) {
    return new LoginResponse(dto.token());
  }
}
