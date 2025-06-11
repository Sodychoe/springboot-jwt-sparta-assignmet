package com.haein.jwt.fixture.repository;

import com.haein.jwt.domain.User;
import com.haein.jwt.repository.UserRepository;
import java.util.List;
import java.util.Optional;

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

  @Override
  public Optional<User> findByUsername(String username) {
    if (entities.get(0).getUsername().equals(username)) {
      return Optional.of(entities.get(0));
    }

    return Optional.empty();
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
