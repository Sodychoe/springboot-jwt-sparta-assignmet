package com.haein.jwt.repository;

import com.haein.jwt.domain.User;
import java.util.List;

public class UserRepositoryStub implements UserRepository {

  private final List<User> entities = createDummyData();

  @Override
  public boolean existsByUsername(String username) {
    return entities.get(0).getUsername().equals(username);
  }

  @Override
  public User save(User user) {
    User savedUser = User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .nickname(user.getNickname())
        .build();

    savedUser.setId(2L);

    return savedUser;
  }

  private List<User> createDummyData() {
    User existingUser = User.builder()
        .username("existingUser")
        .password("1234test")
        .nickname("existingNickname")
        .build();

    existingUser.setId(1L);
    return List.of(existingUser);
  }
}
