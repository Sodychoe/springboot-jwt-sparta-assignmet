package com.haein.jwt.controller.dto.response;

import com.haein.jwt.service.dto.response.AuthorizeAdminRoleResponseDto;
import java.util.List;

public record AuthorizeAdminRoleResponse(
    String username,
    String nickname,
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

  private record Role(
      String role
  ) {}
}
