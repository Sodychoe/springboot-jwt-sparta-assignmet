package com.haein.jwt.controller;

import com.haein.jwt.controller.dto.request.LoginRequest;
import com.haein.jwt.controller.dto.request.SignupRequest;
import com.haein.jwt.controller.dto.response.LoginResponse;
import com.haein.jwt.controller.dto.response.SignupResponse;
import com.haein.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public SignupResponse signup(@RequestBody SignupRequest request) {
    return SignupResponse.from(userService.signup(request.toApplicationDto(request)));
  }

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest request) {
    return LoginResponse.from(userService.login(request.toApplicationDto(request)));
  }
}
