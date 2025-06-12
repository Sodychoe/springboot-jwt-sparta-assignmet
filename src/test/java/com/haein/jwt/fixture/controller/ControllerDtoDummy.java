package com.haein.jwt.fixture.controller;

import com.haein.jwt.controller.dto.request.SignupRequest;

public class ControllerDtoDummy {
  private final SignupRequest newUser;
  private final SignupRequest existingUser;

  public ControllerDtoDummy(SignupRequest newUser, SignupRequest existingUser) {
    this.newUser = newUser;
    this.existingUser = existingUser;
  }

  public static ControllerDtoDummy init() {
    return new ControllerDtoDummy(
        new SignupRequest("newUser", "1234test", "newNickname"),
        new SignupRequest("existingUser", "1234test", "existingNickname")
    );
  }

  public SignupRequest getNewSignupUserRequest() {
    return newUser;
  }

  public SignupRequest getAlreadyExistingUser() {
    return existingUser;
  }
}
