package com.haein.jwt.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haein.jwt.controller.dto.request.SignupRequest;
import com.haein.jwt.fixture.controller.ControllerDtoDummy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
@TestInstance(Lifecycle.PER_CLASS)
public class JwtIntegrationTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  ControllerDtoDummy userDummy = ControllerDtoDummy.init();

  @Test
  @DisplayName("새로운 사용자가 회원가입에 성공한다")
  void signup() throws Exception {

    // given
    SignupRequest newSignupUserRequest = userDummy.getNewSignupUserRequest();

    // when
    mvc.perform(
            post("/api/v1/users/signup").content(objectMapper.writeValueAsString(newSignupUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value(newSignupUserRequest.username()))
        .andExpect(jsonPath("$.password").exists());

    // then
  }
}
