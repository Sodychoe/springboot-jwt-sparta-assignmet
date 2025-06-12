package com.haein.jwt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.haein.jwt.fixture.repository.UserRepositoryStub;
import com.haein.jwt.service.dto.response.AuthorizeAdminRoleResponseDto;
import com.haein.jwt.service.exception.ServiceErrorCode;
import com.haein.jwt.service.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

  @InjectMocks
  AdminService sut;

  @Spy
  UserRepositoryStub stub;

  @Nested
  class authorize {

    @Test
    @DisplayName("이미 관리자인 사용자의 관리자 권한 부여 요청을 받으면 USER_ALREADY_ADMIN 에러 코드를 담은 커스텀 에러를 던진다")
    void givenAlreadyAdminUser_whenAuthorize_thenThrowServiceException() {
      // given
      long adminUserId = 2L;

      // when & then
      assertThatThrownBy(() -> sut.authorizeAdminRole(adminUserId))
          .isInstanceOf(ServiceException.class)
          .hasMessage(ServiceErrorCode.USER_ALREADY_ADMIN.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 사용자 ID를 받으면 USER_NOT_FOUND 에러 코드를 담은 커스텀 에러를 던진다")
    void givenNonExistingUser_whenAuthorize_thenThrowServiceException() {
      // given
      long nonExistingUserId = 5L;

      // when & then
      assertThatThrownBy(() -> sut.authorizeAdminRole(nonExistingUserId))
          .isInstanceOf(ServiceException.class)
          .hasMessage(ServiceErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("요청한 사용자 ID로 관리자 권한을 부여한다")
    void givenAuthorizeAdminRoleRequestDto_whenAuthorize_thenSuccess() {
      // given
      long nonAdminUserId = 1L;

      // when
      AuthorizeAdminRoleResponseDto dto = sut.authorizeAdminRole(nonAdminUserId);

      // then
      assertThat(dto.isAdmin()).isTrue();
    }
  }

}
