package com.haein.jwt.service.dto.response;

public record SignupResponseDto(
    String username,
    String password,
    String nickname
) {
}
