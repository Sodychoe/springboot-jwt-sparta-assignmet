package com.haein.jwt.service.dto.response;

import com.haein.jwt.domain.User;

public record SignupResponseDto(
    Long id,
    String username,
    String password,
    String nickname
) {

  public static SignupResponseDto from(User user) {
    return new SignupResponseDto(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getNickname()
    );
  }
}
