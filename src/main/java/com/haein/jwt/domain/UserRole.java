package com.haein.jwt.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
  ADMIN("ROLE_ADMIN"),
  NORMAL("ROLE_NORMAL");

  private final String role;
}
