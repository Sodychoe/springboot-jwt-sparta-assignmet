package com.haein.jwt.service;

import com.haein.jwt.domain.User;
import com.haein.jwt.repository.UserRepository;
import com.haein.jwt.service.dto.response.AuthorizeAdminRoleResponseDto;
import com.haein.jwt.service.exception.ServiceErrorCode;
import com.haein.jwt.service.exception.ServiceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final UserRepository userRepository;

  public AuthorizeAdminRoleResponseDto authorizeAdminRole(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> ServiceException.from(ServiceErrorCode.USER_NOT_FOUND)
    );

    if (user.isAdmin()) {
      throw ServiceException.from(ServiceErrorCode.USER_ALREADY_ADMIN);
    }

    user.authorizeAdminRole();
    User savedUser = userRepository.save(user);

    return AuthorizeAdminRoleResponseDto.of(
        savedUser.getUsername(),
        savedUser.getNickname(),
        List.of(savedUser.getRole())
    );
  }
}
