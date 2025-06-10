package com.haein.jwt.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haein.jwt.controller.dto.request.SignupRequest;
import com.haein.jwt.controller.exception.ErrorCode;
import com.haein.jwt.controller.exception.ServiceException;
import com.haein.jwt.fixture.UserDummy;
import com.haein.jwt.service.UserService;
import com.haein.jwt.service.dto.response.SignupResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.filter.OncePerRequestFilter;

@WebMvcTest(value = UserController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
            classes = {OncePerRequestFilter.class})
    })
public class UserControllerTest {

  @SpyBean
  UserService userService;

  @Autowired
  MockMvc mvc;

  UserDummy userDummy = UserDummy.init();

  ObjectMapper objectMapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  @Nested
  class Signup {

    @Test
    @DisplayName("이미 가입된 사용자라면 409 Conflict 상태와 에러 응답을 반환한다")
    void givenSignupRequest_whenAlreadyExists_then409conflict() throws Exception {
      // given
      SignupRequest existingUser = userDummy.getAlreadyExistingUser();

      given(userService.signup(any())).willThrow(
          ServiceException.from(ErrorCode.USER_ALREADY_EXISTS));

      // when
      MvcResult result = mvc.perform(post("/api/v1/users/signup")
              .content(objectMapper.writeValueAsString(existingUser))
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isConflict())
          .andReturn();

      // then
      String content = result.getResponse().getContentAsString();
      JsonNode body = objectMapper.readTree(content);
      JsonNode errorField = body.get("error");

      assertThat(errorField).isNotNull();
      assertThat(errorField.get("code").asText()).isEqualTo("USER_ALREADY_EXISTS");
      assertThat(errorField.get("message").asText()).isEqualTo("이미 가입된 사용자입니다.");
    }


    @Test
    @DisplayName("사용자 이름, 비밀번호, 닉네임 정보를 전달하여 회원가입에 성공하면 200 OK 상태의 응답을 반환한다")
    void givenSignupRequest_whenSuccess_then200ok()
        throws Exception {
      // given
      SignupRequest newUser = userDummy.getNewSignupUserRequest();

      given(userService.signup(any())).willReturn(new SignupResponseDto(
          newUser.username(),
          newUser.password(),
          newUser.nickname()
      ));

      // when
      MvcResult result = mvc.perform(post("/api/v1/users/signup")
              .content(objectMapper.writeValueAsString(newUser))
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andReturn();

      // then
      String content = result.getResponse().getContentAsString();
      JsonNode body = objectMapper.readTree(content);

      assertThat(body.get("username").asText()).isEqualTo(newUser.username());
      assertThat(body.get("password").asText()).isEqualTo(newUser.password());
      assertThat(body.get("nickname").asText()).isEqualTo(newUser.nickname());
    }

  }

  @Nested
  class Login {

    @Test
    @DisplayName("로그인 정보가 잘못되어 로그인에 실패하면 401 Unauthorized 상태의 응답을 반환한다")
    void givenLoginRequest_whenFail_then401unauthorized() throws Exception {
      // given
      Loginrequest wrongLoginRequest = new Loginrequest("wrongUsername", "wrongPassword");

      given(userService.login(any())).willThrow(
          ServiceException.from(ErrorCode.INVALID_CREDENTIALS));

      // when
      MvcResult result = mvc.perform(post("/api/v1/users/login")
              .content(objectMapper.writeValueAsString(wrongLoginRequest))
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isUnauthorized())
          .andReturn();

      // then
      String content = result.getResponse().getContentAsString();
      JsonNode body = objectMapper.readTree(content);
      JsonNode errorField = body.get("error");

      assertThat(errorField).isNotNull();
      assertThat(errorField.get("code").asText()).isEqualTo("INVALID_CREDENTIALS");
      assertThat(errorField.get("message").asText()).isEqualTo("아이디 또는 비밀번호가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("올바른 사용자 이름과 비밀번호를 전달하면 로그인에 성공하면 200 OK 상태의 응답을 반환한다")
    void givenLoginRequest_whenSuccess_then200ok() throws Exception {
      // given
      LoginRequest normalLoginRequest = new LoginRequest(
          userDummy.getAlreadyExistingUser().username(),
          userDummy.getAlreadyExistingUser().password()
      );

      given(userService.login(any())).willReturn(new LoginResponse(
          normalLoginRequest.username(),
          normalLoginRequest.password()
      ));

      // when
      MvcResult result = mvc.perform(post("/api/v1/users/login")
              .content(objectMapper.writeValueAsString(normalLoginRequest))
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andReturn();

      // then
      String content = result.getResponse().getContentAsString();
      JsonNode body = objectMapper.readTree(content);

      assertThat(body.get("username").asText()).isEqualTo(normalLoginRequest.username());
      assertThat(body.get("password").asText()).isEqualTo(normalLoginRequest.password());
    }
  }
}
