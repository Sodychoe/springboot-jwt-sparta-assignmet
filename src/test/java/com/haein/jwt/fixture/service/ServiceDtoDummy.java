package com.haein.jwt.fixture.service;

import com.haein.jwt.service.dto.request.LoginRequestDto;
import com.haein.jwt.service.dto.request.SignupRequestDto;

public class ServiceDtoDummy {

  private final SignupRequestDto nonExistingSignupUser;
  private final SignupRequestDto existingSignupUser;

  private final LoginRequestDto nonExistingLoginUser;
  private final LoginRequestDto existingLoginUser;

  public ServiceDtoDummy(SignupRequestDto nonExistingSignupUser,
      SignupRequestDto existingSignupUser,
      LoginRequestDto nonExistingLoginUser, LoginRequestDto existingLoginUser) {
    this.nonExistingSignupUser = nonExistingSignupUser;
    this.existingSignupUser = existingSignupUser;
    this.nonExistingLoginUser = nonExistingLoginUser;
    this.existingLoginUser = existingLoginUser;
  }

  public static ServiceDtoDummy init() {
    return new ServiceDtoDummy(
        new SignupRequestDto("nonExistingUser", "1234test", "newNickname"),
        new SignupRequestDto("existingUser", "1234test", "existingNickname"),

        new LoginRequestDto("nonExistingUser", "1234test"),
        new LoginRequestDto("existingUser", "1234test")
    );
  }

  public SignupRequestDto getNonExistingSignupUser() {
    return nonExistingSignupUser;
  }

  public SignupRequestDto getAlreadyExistingSignupUser() {
    return existingSignupUser;
  }

  public LoginRequestDto getNonExistingLoginUser() {
    return nonExistingLoginUser;
  }

  public LoginRequestDto getExistingLoginUser() {
    return existingLoginUser;
  }

  public LoginRequestDto getWrongPasswordLoginUser() {
    return new LoginRequestDto(
        existingLoginUser.username(),
        "wrongPassword"
    );
  }
}
