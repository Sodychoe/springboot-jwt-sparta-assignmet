package com.haein.jwt.fixture.repository;

import com.haein.jwt.domain.User;
import com.haein.jwt.repository.UserRepository;
import java.util.ArrayList;
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
    if (entities.contains(user)) {
      update(user);
      return user;
    }


    User savedUser = User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .nickname(user.getNickname())
        .build();

    savedUser.setId(3L);

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

    User adminRuser = User.builder()
        .username("adminUser")
        .password("1234test")
        .nickname("adminNickname")
        .build();

    adminRuser.authorizeAdminRole();
    adminRuser.setId(2L);

    ArrayList<User> list = new ArrayList<>();
    list.add(existingUser);
    list.add(adminRuser);

    return list;
  }

  @Override
  public Optional<User> findById(Long userId) {
    return entities.stream()
        .filter(user -> user.getId().equals(userId))
        .findFirst();
  }

  private void update(User user) {
    entities.remove(user);
    entities.add(user);
  }
}


