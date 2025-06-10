package com.haein.jwt.fixture.service;

import com.haein.jwt.service.dto.request.SignupRequestDto;

public class ServiceDtoDummy {

  private final SignupRequestDto newUser;
  private final SignupRequestDto existingUser;

  public ServiceDtoDummy(SignupRequestDto newUser, SignupRequestDto existingUser) {
    this.newUser = newUser;
    this.existingUser = existingUser;
  }

  public static ServiceDtoDummy init() {
    return new ServiceDtoDummy(
        new SignupRequestDto("newUser", "1234test", "newNickname"),
        new SignupRequestDto("existingUser", "1234test", "existingNickname")
    );
  }

  public SignupRequestDto getNewSignupUserRequest() {
    return newUser;
  }

  public SignupRequestDto getAlreadyExistingUser() {
    return existingUser;
  }
}
