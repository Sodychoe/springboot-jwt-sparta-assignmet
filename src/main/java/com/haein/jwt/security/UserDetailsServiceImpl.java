package com.haein.jwt.security;

import com.haein.jwt.domain.User;
import com.haein.jwt.repository.UserRepository;
import com.haein.jwt.service.exception.ServiceErrorCode;
import com.haein.jwt.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      User user = userRepository.findByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException("User not found"));

      return UsersDetailsImpl
          .builder()
          .user(user)
          .build();
    } catch (UsernameNotFoundException e) {
      throw ServiceException.from(ServiceErrorCode.USER_NOT_FOUND);
    }
  }
}
