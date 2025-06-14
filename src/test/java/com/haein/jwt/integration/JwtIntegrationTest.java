package com.haein.jwt.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haein.jwt.controller.dto.request.LoginRequest;
import com.haein.jwt.controller.dto.request.SignupRequest;
import com.haein.jwt.fixture.controller.ControllerDtoDummy;
import com.haein.jwt.service.exception.ServiceErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("security-test")
public class JwtIntegrationTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  ControllerDtoDummy userDummy = ControllerDtoDummy.init();


  @Test
  @DisplayName("새로운 사용자가 회원가입에 성공 후 로그인에 성공한다")
  void signup() throws Exception {

    // given
    SignupRequest newSignupUserRequest = userDummy.getNewSignupUserRequest();

    // 회원가입 성공
    mvc.perform(
            post("/api/v1/users/signup").content(objectMapper.writeValueAsString(newSignupUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value(newSignupUserRequest.username()))
        .andExpect(jsonPath("$.password").value(newSignupUserRequest.password()))
        .andExpect(jsonPath("$.nickname").value(newSignupUserRequest.nickname()));

    // 중복 가입 불가
    mvc.perform(
            post("/api/v1/users/signup").content(objectMapper.writeValueAsString(newSignupUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.error.code").value(ServiceErrorCode.USER_ALREADY_EXISTS.getCode()))
        .andExpect(
            jsonPath("$.error.message").value(ServiceErrorCode.USER_ALREADY_EXISTS.getMessage()));

    // 로그인 성공
    LoginRequest normalLoginRequest = new LoginRequest(
        userDummy.getNewSignupUserRequest().username(),
        userDummy.getNewSignupUserRequest().password()
    );

    mvc.perform(post("/api/v1/users/login")
            .content(objectMapper.writeValueAsString(normalLoginRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isNotEmpty());

  }
}
