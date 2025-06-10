package com.haein.jwt.repository;


import com.haein.jwt.domain.User;

public interface UserRepository {

  boolean existsByUsername(String username);

  User save(User user);
}
