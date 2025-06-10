package com.haein.jwt.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

import com.haein.jwt.fixture.service.ServiceDtoDummy;
import com.haein.jwt.service.dto.request.SignupRequestDto;
import com.haein.jwt.service.dto.response.SignupResponseDto;
import com.haein.jwt.service.exception.ErrorCode;
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

      // when
      sut.signup(existingUser);

      // then
      assertThrows(ServiceException.class, () -> sut.signup(existingUser),
          ErrorCode.USER_ALREADY_EXISTS.getMessage());
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
      then(stub).should().save(any(User.class));
    }
  }
}
