package com.haein.jwt.repository;

import com.haein.jwt.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "singleton")
public class UserRepositoryImpl implements UserRepository {

  private final List<User> entities = new ArrayList<>();
  private final AtomicLong idGenerator = new AtomicLong(1L);

  @Override
  public User save(User user) {
    long id = idGenerator.getAndIncrement();
    user.setId(id);
    entities.add(user);

    return user;
  }

  @Override
  public boolean existsByUsername(String username) {
    return entities.stream()
        .anyMatch(user -> user.getUsername().equals(username));
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return entities.stream()
        .filter(user -> user.getUsername().equals(username))
        .findFirst();
  }
}
