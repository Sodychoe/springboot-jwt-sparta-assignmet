package com.haein.jwt.controller;

import com.haein.jwt.controller.dto.response.AuthorizeAdminRoleResponse;
import com.haein.jwt.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class AdminController {

  private final AdminService adminService;

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
