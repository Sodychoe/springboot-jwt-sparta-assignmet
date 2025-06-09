package com.haein.jwt.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haein.jwt.fixture.UserDummy;
import com.haein.jwt.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(UserControllerTest.class)
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
    @DisplayName("사용자 이름, 비밀번호, 닉네임 정보를 전달하여 회원가입에 성공하면 200 OK 상태의 응답을 반환한다")
    void givenSignupRequest_whenSuccess_then200ok() throws JsonProcessingException {
      // given
      SignupReqeustDto newUser = userDummy.getNewSignupUser();

      given(userService.signup(newUser)).willReturn(newUser);

      // when
      MvcResult result = mvc.perform(post("/signup")
              .content(newUser)
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

    @Test
    @DisplayName("이미 가입된 사용자라면 409 Conflict 상태와 에러 응답을 반환한다")
    void givenSignupRequest_whenAlreadyExists_then409conflict() {
      // given
      SignupReqeustDto existingUser = userDummy.getAlreadyExistingUser();

      given(userService.signup(newUser)).willReturn(existingUser);

      // when
      MvcResult result = mvc.perform(post("/signup")
              .content(newUser)
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
  }
}
