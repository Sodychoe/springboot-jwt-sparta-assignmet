package com.haein.jwt.security;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.haein.jwt.fixture.repository.UserRepositoryStub;
import com.haein.jwt.fixture.service.ServiceDtoDummy;
import com.haein.jwt.repository.UserRepository;
import com.haein.jwt.service.dto.request.SignupRequestDto;
import com.haein.jwt.service.exception.ServiceErrorCode;
import com.haein.jwt.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

class UserDetailsServiceImplTest {

  UserDetailsServiceImpl sut;
  UserRepository stub;

  ServiceDtoDummy userDummy = ServiceDtoDummy.init();

  @BeforeEach
  void setUp() {
    stub = new UserRepositoryStub();
    sut = new UserDetailsServiceImpl(stub);
  }

  @Test
  @DisplayName("존재하지 않는 유저인 경우 USER_NOT_FOUND 에러코드가 포함된 커스텀 에러를 던진다")
  void givenUnExistingUser_whenLoadUser_thenThrowServiceException() {
    // given
    String notExistingUsername = "UNKNOWN";

    // when then
    assertThatThrownBy(() -> sut.loadUserByUsername(notExistingUsername))
        .isInstanceOf(ServiceException.class)
        .hasMessage(ServiceErrorCode.USER_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("이미 존재하는 유저를 유저이름으로 조회하면 성공한다")
  void givenExistingUser_whenLoadUser_thenSuccess() {
    // given
    SignupRequestDto existingUser = userDummy.getAlreadyExistingUser();

    // when
    UserDetails user = sut.loadUserByUsername(existingUser.username());

    // then
    assertThat(user.getUsername()).isEqualTo(existingUser.username());
  }
}
