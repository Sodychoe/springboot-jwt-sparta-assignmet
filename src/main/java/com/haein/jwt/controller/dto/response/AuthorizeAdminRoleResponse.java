package com.haein.jwt.controller.dto.response;

import com.haein.jwt.service.dto.response.AuthorizeAdminRoleResponseDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record AuthorizeAdminRoleResponse(
    @Schema(description = "권한이 변경된 사용자 이름", example = "test1")
    String username,
    @Schema(description = "권한이 변경된 사용자 닉네임", example = "nickname")
    String nickname,
    @Schema(description = "권한이 변경된 사용자의 권한 목록")
    List<Role> roles
) {

  public static AuthorizeAdminRoleResponse from(
      AuthorizeAdminRoleResponseDto authorizeAdminRoleResponseDto) {
    return new AuthorizeAdminRoleResponse(
        authorizeAdminRoleResponseDto.username(),
        authorizeAdminRoleResponseDto.nickname(),
        authorizeAdminRoleResponseDto.getRoles().stream()
            .map(Role::new)
            .toList()
    );
  }

  @Hidden
  private record Role(
      @Schema(description = "권한 이름", example = "ROLE_ADMIN")
      String role
  ) {

  }
}
