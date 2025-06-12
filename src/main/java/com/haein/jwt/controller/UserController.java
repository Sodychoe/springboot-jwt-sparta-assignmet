package com.haein.jwt.controller;

import com.haein.jwt.controller.dto.request.LoginRequest;
import com.haein.jwt.controller.dto.request.SignupRequest;
import com.haein.jwt.controller.dto.response.LoginResponse;
import com.haein.jwt.controller.dto.response.SignupResponse;
import com.haein.jwt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 관리 API", description = "사용자 인증, 인가에 관한 기능을 제공하는 컨트롤러입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @Operation(summary = "회원가입", description = "서비스에 회원가입을 진행하는 API 입니다")
  @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(
      schema = @Schema(implementation = SignupResponse.class),
      mediaType = MediaType.APPLICATION_JSON_VALUE
  ))
  @PostMapping("/signup")
  public ResponseEntity<SignupResponse> signup(
      @RequestBody
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          content = @Content(
              schema = @Schema(implementation = SignupRequest.class),
              mediaType = MediaType.APPLICATION_JSON_VALUE
          )
      )
      SignupRequest request) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(SignupResponse.from(userService.signup(request.toApplicationDto(request))));
  }

  @Operation(summary = "로그인", description = "서비스에 로그인을 진행하는 API 입니다")
  @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(
      schema = @Schema(implementation = LoginResponse.class),
      mediaType = MediaType.APPLICATION_JSON_VALUE
  ))
  @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호가 잘못됨", content = @Content(
      mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(
      value = """
          {
            "error": {
              "code": "INVALID_CREDENTIALS",
              "message": "아이디 또는 비밀번호가 올바르지 않습니다."
            }
          }"""
  )))
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @RequestBody
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          content = @Content(
              schema = @Schema(implementation = LoginRequest.class),
              mediaType = MediaType.APPLICATION_JSON_VALUE
          )
      )
      LoginRequest request) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(LoginResponse.from(userService.login(request.toApplicationDto(request))));
  }
}
