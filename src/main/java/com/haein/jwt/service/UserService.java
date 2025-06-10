package com.haein.jwt.service;

import com.haein.jwt.domain.User;
import com.haein.jwt.repository.UserRepository;
import com.haein.jwt.service.dto.request.LoginRequestDto;
import com.haein.jwt.service.dto.request.SignupRequestDto;
import com.haein.jwt.service.dto.response.LoginResponseDto;
import com.haein.jwt.service.dto.response.SignupResponseDto;
import com.haein.jwt.service.exception.ErrorCode;
import com.haein.jwt.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public SignupResponseDto signup(SignupRequestDto applicationDto) {
    if (validateExistingUser(applicationDto)) {
      throw ServiceException.from(ErrorCode.USER_ALREADY_EXISTS);
    }

    User user = User.builder()
        .username(applicationDto.username())
        .password(applicationDto.password())
        .nickname(applicationDto.nickname())
        .build();

    User savedUser = userRepository.save(user);

    return SignupResponseDto.from(savedUser);
  }

  public LoginResponseDto login(LoginRequestDto applicationDto) {
    return null;
  }

  private boolean validateExistingUser(SignupRequestDto applicationDto) {
    return userRepository.existsByUsername(applicationDto.username());
  }
}
