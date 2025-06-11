package com.haein.jwt.security;

import com.haein.jwt.domain.User;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsersDetailsImpl implements UserDetails {

  private final User user;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

    return List.of(authority);
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Builder
  private UsersDetailsImpl(User user) {
    this.user = user;
  }
}
