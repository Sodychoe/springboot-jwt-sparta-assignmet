package com.haein.jwt.service.dto.response;

import java.util.List;

public record AuthorizeAdminRoleResponseDto(
    String username,
    String nickname,
    List<Role> roles
) {

  public boolean isAdmin() {
    return roles.stream().anyMatch(role -> role.roleName.equals("ROLE_ADMIN"));
  }

  public static AuthorizeAdminRoleResponseDto of(String username, String nickname, List<String> roles) {
    return new AuthorizeAdminRoleResponseDto(
        username,
        nickname,
        roles.stream().map(Role::of).toList()
    );
  }

  private record Role(
      String roleName
  ) {

    public static Role of(String role) {
      return new Role(role);
    }
  }
}
