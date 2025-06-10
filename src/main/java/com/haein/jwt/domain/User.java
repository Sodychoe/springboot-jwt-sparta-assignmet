package com.haein.jwt.domain;

import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class User {

  private final String username;
  private final String password;
  private final String nickname;
  @Setter
  private Long id;
  private UserRole role;

  @Builder
  private User(String username, String password, String nickname) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.role = UserRole.NORMAL;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User user)) {
      return false;
    }
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
