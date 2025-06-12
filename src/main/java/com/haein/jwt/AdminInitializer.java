package com.haein.jwt;

import com.haein.jwt.domain.User;
import com.haein.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class AdminInitializer implements ApplicationRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(ApplicationArguments args) {
    User adminRuser = User.builder()
        .username("admin")
        .password(passwordEncoder.encode("admin123"))
        .nickname("adminNickname")
        .build();

    adminRuser.authorizeAdminRole();
    userRepository.save(adminRuser);

    log.info("시스템 관리자 계청 생성 : id={}, username={}, password={}", adminRuser.getId(),
        "admin", "admin123");
  }
}
