package com.haein.jwt.controller;

import com.haein.jwt.controller.dto.response.AuthorizeAdminRoleResponse;
import com.haein.jwt.security.SecurityErrorCode;
import com.haein.jwt.service.AdminService;
import com.haein.jwt.service.exception.ServiceErrorCode;
import com.haein.jwt.swagger.ApiErrorResponseExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자 API", description = "관리자 권한을 가진 사용자가 접근 가능한 컨트롤러입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class AdminController {

  private final AdminService adminService;

  @Operation(summary = "관리자 권한 부여", description = "관리자 권한으로 다른 사용자에게 관리자 권한을 부여할 때 사용하는 API 엔드포인트")
  @ApiResponse(responseCode = "200", description = "관리자 권한 부여 성공", content = @Content(
      schema = @Schema(implementation = AuthorizeAdminRoleResponse.class),
      mediaType = MediaType.APPLICATION_JSON_VALUE
  ))
  @ApiErrorResponseExample(ServiceErrorCode.USER_NOT_FOUND)
  @ApiErrorResponseExample(ServiceErrorCode.USER_ALREADY_ADMIN)
  @ApiErrorResponseExample(ServiceErrorCode.NO_AUTHORIZATION)
  @Parameter(name = "userId", description = "권한을 부여할 사용자의 ID", required = true, example = "2")
  @PatchMapping("/{userId}/roles")
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
  public ResponseEntity<AuthorizeAdminRoleResponse> authorizeAdminRole(@PathVariable
      (name = "userId") Long userId) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(AuthorizeAdminRoleResponse.from(adminService.authorizeAdminRole(userId)));
  }
}
