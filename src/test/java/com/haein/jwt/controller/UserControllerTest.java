package com.haein.jwt.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UserControllerTest {

  @Nested
  class Signup {

    @Test
    @DisplayName("사용자 이름, 비밀번호, 닉네임 정보를 전달하여 회원가입에 성공하면 200 OK 상태의 응답을 반환한다")
    void givenSignupRequest_whenSuccess_then200ok() {
      // given

      // when

      // then
    }

    @Test
    @DisplayName("이미 가입된 사용자라면 409 Conflict 상태와 에러 응답을 반환한다")
    void givenSignupRequest_whenAlreadyExists_then409conflict() {
      // given

      // when

      // then
    }
  }
}
