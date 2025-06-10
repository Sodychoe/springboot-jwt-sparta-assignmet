package com.haein.jwt.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

  UserService sut;

  @Nested
  class Signup {

    @Test
    @DisplayName("이미 가입한 사용자인 경우 USER_ALREADY_EXISTS 에러 코드를 담은 커스텀 에러를 던진다")
    void givenExistingUserDto_whenSignup_thenThrowServiceException() {
      // given

      // when

      // then
    }

    @Test
    @DisplayName("새로운 사용자의 회원가입 정보를 User 객체로 변환하여 Repository 에 추가하고 Dto 를 반환한다")
    void givenNewSignupDto_whenSignup_thenSaveAndReturnsDto() {
      // given

      // when

      // then
    }
  }
}
