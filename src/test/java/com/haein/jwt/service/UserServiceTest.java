package com.haein.jwt.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.haein.jwt.fixture.repository.UserRepositoryStub;
import com.haein.jwt.fixture.service.ServiceDtoDummy;
import com.haein.jwt.repository.UserRepository;
import com.haein.jwt.service.dto.request.SignupRequestDto;
import com.haein.jwt.service.dto.response.SignupResponseDto;
import com.haein.jwt.service.exception.ServiceErrorCode;
import com.haein.jwt.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

  UserService sut;

  UserRepository stub;

  ServiceDtoDummy userDummy = ServiceDtoDummy.init();

  @BeforeEach
  void setUp() {
    stub = new UserRepositoryStub();
    sut = new UserService(stub);
  }

  @Nested
  class Signup {

    @Test
    @DisplayName("이미 가입한 사용자인 경우 USER_ALREADY_EXISTS 에러 코드를 담은 커스텀 에러를 던진다")
    void givenExistingUserDto_whenSignup_thenThrowServiceException() {
      // given
      SignupRequestDto existingUser = userDummy.getAlreadyExistingUser();

      // when & then
      assertThatThrownBy(() -> sut.signup(existingUser))
          .isInstanceOf(ServiceException.class)
          .hasMessage(ServiceErrorCode.USER_ALREADY_EXISTS.getMessage());

    }

    @Test
    @DisplayName("새로운 사용자의 회원가입 정보를 User 객체로 변환하여 Repository 에 추가하고 Dto 를 반환한다")
    void givenNewSignupDto_whenSignup_thenSaveAndReturnsDto() {
      // given
      SignupRequestDto newUser = userDummy.getNewSignupUserRequest();

      // when
      SignupResponseDto dto = sut.signup(newUser);

      // then
      assertThat(dto.username()).isEqualTo(newUser.username());
      assertThat(dto.password()).isEqualTo(newUser.password());
      assertThat(dto.nickname()).isEqualTo(newUser.nickname());
    }
  }

  @Nested
  class Login {

    @Test
    @DisplayName("존재하지 않는 사용자의 로그인 정보를 받으면 INVALID_CREDENTIALS 에러 코드를 담은 커스텀 에러를 던진다")
    void givenNonExistingUserDto_whenLogin_thenThrowServiceException() {
      // given

      // when & then
      assertThatThrownBy(() -> sut.login())
          .isInstanceOf(ServiceException.class)
          .hasMessage(ServiceErrorCode.INVALID_CREDENTIALS.getMessage());
    }

    @Test
    @DisplayName("가입된 사용자의 로그인 정보를 받으면 로그인이 성공하고 엑세스 토큰을 발급한다")
    void givenExistingUserDto_whenLogin_thenReturnAccessToken() {
      // given

      // when

      // then
    }
  }
}
