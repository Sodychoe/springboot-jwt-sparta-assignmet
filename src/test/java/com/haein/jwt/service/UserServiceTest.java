package com.haein.jwt.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.haein.jwt.fixture.repository.UserRepositoryStub;
import com.haein.jwt.fixture.service.ServiceDtoDummy;
import com.haein.jwt.security.JwtUtil;
import com.haein.jwt.service.dto.request.LoginRequestDto;
import com.haein.jwt.service.dto.request.SignupRequestDto;
import com.haein.jwt.service.dto.response.LoginResponseDto;
import com.haein.jwt.service.dto.response.SignupResponseDto;
import com.haein.jwt.service.exception.ServiceErrorCode;
import com.haein.jwt.service.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  UserService sut;

  @Spy
  UserRepositoryStub stub;

  @Mock
  JwtUtil jwtUtil;

  @Spy
  PasswordEncoder passwordEncoder;

  ServiceDtoDummy userDummy = ServiceDtoDummy.init();


  @Nested
  class Signup {

    @Test
    @DisplayName("이미 가입한 사용자인 경우 USER_ALREADY_EXISTS 에러 코드를 담은 커스텀 에러를 던진다")
    void givenExistingUserDto_whenSignup_thenThrowServiceException() {
      // given
      SignupRequestDto existingUser = userDummy.getAlreadyExistingSignupUser();

      // when & then
      assertThatThrownBy(() -> sut.signup(existingUser))
          .isInstanceOf(ServiceException.class)
          .hasMessage(ServiceErrorCode.USER_ALREADY_EXISTS.getMessage());

    }

    @Test
    @DisplayName("새로운 사용자의 회원가입 정보를 User 객체로 변환하여 Repository 에 추가하고 Dto 를 반환한다")
    void givenNewSignupDto_whenSignup_thenSaveAndReturnsDto() {
      // given
      SignupRequestDto newUser = userDummy.getNonExistingSignupUser();
      given(passwordEncoder.encode(anyString())).willReturn(newUser.password());

      // when
      SignupResponseDto dto = sut.signup(newUser);

      // then
      assertThat(dto.username()).isEqualTo(newUser.username());
      assertThat(dto.password()).isEqualTo(newUser.password());
      assertThat(dto.nickname()).isEqualTo(newUser.nickname());
      then(passwordEncoder).should().encode(anyString());
    }
  }

  @Nested
  class Login {

    @Test
    @DisplayName("존재하지 않는 사용자의 로그인 정보를 받으면 INVALID_CREDENTIALS 에러 코드를 담은 커스텀 에러를 던진다")
    void givenNonExistingUserDto_whenLogin_thenThrowServiceException() {
      // given
      LoginRequestDto nonExistingLoginUser = userDummy.getNonExistingLoginUser();

      // when & then
      assertThatThrownBy(() -> sut.login(nonExistingLoginUser))
          .isInstanceOf(ServiceException.class)
          .hasMessage(ServiceErrorCode.INVALID_CREDENTIALS.getMessage());
    }

    @Test
    @DisplayName("가입된 사용자의 로그인 정보를 받으면 로그인이 성공하고 엑세스 토큰을 발급한다")
    void givenExistingUserDto_whenLogin_thenReturnAccessToken() {
      // given
      LoginRequestDto existingLoginUser = userDummy.getExistingLoginUser();
      given(passwordEncoder.matches(anyString(), anyString())).willReturn(Boolean.TRUE);
      given(jwtUtil.createAccessToken(anyString(), anyString())).willReturn("Normal Token");

      // when
      LoginResponseDto dto = sut.login(existingLoginUser);

      // then
      assertThat(dto.token()).isNotNull();
      then(jwtUtil).should().createAccessToken(anyString(), anyString());
    }
  }
}
