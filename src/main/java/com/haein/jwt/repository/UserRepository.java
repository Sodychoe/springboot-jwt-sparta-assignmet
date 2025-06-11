package com.haein.jwt.repository;


import com.haein.jwt.domain.User;
import java.util.Optional;

public interface UserRepository {

  boolean existsByUsername(String username);

  User save(User user);

  Optional<User> findByUsername(String username);
}
