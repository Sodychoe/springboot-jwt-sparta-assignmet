package com.haein.jwt.service;

import com.haein.jwt.domain.User;
import com.haein.jwt.repository.UserRepository;
import com.haein.jwt.security.JwtUtil;
import com.haein.jwt.service.dto.request.LoginRequestDto;
import com.haein.jwt.service.dto.request.SignupRequestDto;
import com.haein.jwt.service.dto.response.LoginResponseDto;
import com.haein.jwt.service.dto.response.SignupResponseDto;
import com.haein.jwt.service.exception.ServiceErrorCode;
import com.haein.jwt.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public SignupResponseDto signup(SignupRequestDto applicationDto) {
    if (validateExistingUser(applicationDto)) {
      throw ServiceException.from(ServiceErrorCode.USER_ALREADY_EXISTS);
    }

    User user = User.builder()
        .username(applicationDto.username())
        .password(passwordEncoder.encode(applicationDto.password()))
        .nickname(applicationDto.nickname())
        .build();

    User savedUser = userRepository.save(user);

    return SignupResponseDto.from(savedUser);
  }

  public LoginResponseDto login(LoginRequestDto dto) {
    String username = dto.username();
    String password = dto.password();

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> ServiceException.from(ServiceErrorCode.INVALID_CREDENTIALS));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw ServiceException.from(ServiceErrorCode.INVALID_CREDENTIALS);
    }

    return LoginResponseDto.from(jwtUtil.createAccessToken(user.getUsername(), user.getRole()));

  }

  private boolean validateExistingUser(SignupRequestDto applicationDto) {
    return userRepository.existsByUsername(applicationDto.username());
  }
}
