package com.haein.jwt.fixture;

import com.haein.jwt.controller.dto.request.SignupRequest;

public class UserDummy {
  private final SignupRequest newUser;
  private final SignupRequest existingUser;

  public UserDummy(SignupRequest newUser, SignupRequest existingUser) {
    this.newUser = newUser;
    this.existingUser = existingUser;
  }

  public static UserDummy init() {
    return new UserDummy(
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
