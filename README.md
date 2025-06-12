# 🗝️ Spring Boot 기반 JWT 인증/인가 서비스 구현 프로젝트

## 📖 프로젝트 개요

JWT 를 이용하여 백엔드 서버에서의 인증/인가 로직을 구현하는 프로젝트입니다.

회원가입과 로그인 기능 및 관리자 권한 부여 기능을 구현하여

사용자 인증 및 역할 기반 인가 메커니즘을 구현하였습니다.

구현한 기능에 대한 단위 테스트 및 통합 테스트와

Swagger 를 이용한 API 문서화를 완료하였습니다.

## 🟦 주요 기능

- 회원 가입
    - 새로운 사용자가 회원가입을 할 수 있습니다.
    - 이미 가입한 사용자인지 검증합니다.
    - 사용자는 일반 사용자 역할(ROLE_NORMAL) 로만 회원가입할 수 있습니다.
- 로그인
    - 사용자가 서비스에 로그인 할 수 있습니다.
    - 로그인이 성공하면 Access Token 을 발급합니다.
    - 서비스에 가입한 유저인 지 검증합니다.
    - 비밀번호가 올바른 지 검증합니다
- 관리자 권한 부여
    - 관리자는 다른 사용자에게 관리자 권한을 부여할 수 있습니다.
    - 관리자가 아닌 사용자가 앤드포엔트에 접근하는 지 검증합니다.
    - 권한을 부여할 사용자가 실제 가입한 사용자인지 검증합니다.

## 🚀 서비스 실행 환경

이 서비스는 현재 Docker-Compose 를 이용하여 AWS EC2 인스턴스에 배포되어 있으며 Nginx 를 이용해
Https 프로토콜에 대한 리버스 프록싱이 적용되어 있습니다.

- 서비스 주소 : https://barointern-13-haein.n-e.kr
- 로컬 실행 :
    - 프로젝트를 로컬에 내려받아 실행하기 위해선 아래와 같은 `.env` 파일을 설정해야 합니다.
    - 환경변수 설정 후 `./gradlew clean build` 명령어로 jar 파일을 얻을 수 있습니다.

```dotenv
JWT_SECRET_KEY=JWTTOKEN
JWT_ACCESS_EXPIRATION=6000000000
```

## 🔧 기술 스택

- Java 17
- Gradle 8.14
- Spring Boot 3.3
- Spring Security 6.3
- Docker
- Nginx
- Github Actions
- AWS EC2

## 📓 API 문서 (Swagger)

모든 API 스펙은 다음 링크에서 확인하실 수 있습니다.

- Swagger : [API 문서](https://barointern-13-haein.n-e.kr/swagger-ui/index.html)

## ⚒️ 구현 중 고민하거나 개선했던 부분들

- 각 요청의 응답 상태에 맞는 HTTP 상태 코드 반환
    - 비밀번호가 맞지 않는 상황은 인증 실패이므로 401 Unauthorized
    - 이미 회원가입이 된 상황은 요청의 데이터가 서버와 충돌하는 상황이므로 409 Conflict
    - 관리자 권한이 아닌데 API 접근하는 상황은 인가 실패이므로 403 Forbidden 등
    - 각 상황에 대해 적절한 상태 코드를 생각하면서 공통 예외 응답으로 반환하도록 구현하였다


- 각 계층의 단위 테스트 구현
    - 컨트롤러, 서비스 등 계층별로 슬라이스 테스트를 진행해 검증하고자 하는 로직에 집중하고 테스트를 간소화했습니다.
    - 정상 흐름이 아닌 예외 처리 흐름에 대한 부분도 모두 계층별로 테스트 코드를 작성했습니다.
    - Jwt 토큰 검증에 대한 부분도 예외 상황에 대한 단위 테스트를 진행했습니다.


- `Spring Security` 의 `FilterChain` 에서 발생하는 예외 핸들링
    - 서비스에서 발생하는 예외는 비즈니스 `ServiceException` 을 던지게 하여 API 에서도 공통 예외 응답을 반환하도록 하고 있습니다.
    - 하지만 `FilterCahin` 은 서블릿 컨텍스트가 아니기 때문에 `@RestControllerAdvice` 의 도움을 받을 수 없습니다.
    - JWT 인증, 인가와 관련된 예외는 커스텀 예외로 감싸도록 했기 때문에 `HandlerExceptionResolver` 을 이용해 서블릿 컨텍스트로 예외를 넘겨 공통
      예외 응답을 반환하도록 처리함


- `Swagger` 에서 예제를 작성할 떄 `Enum` 으로 작성해 둔 에러 코드를 활용하고자 함
    - `Swagger` 의 `@ApiResponse` 를 활용해 각 예외 응답에 대한 예제를 작성할 때 String Literal 로만 작성하는 상황이 불편했습니다.
    - 특히, 이미 상태 코드와, 서비스 에러 코드, 에러 메시지를 저장한 상수 `Enum` 이 있는데 이를 다시 Literal 로 적어야 하는 점이 마음에 들지 않았습니다.
    - 이를 해결하기 위해 `@ApiErrorResponseExample` 라는 에너테이션을 작성해서 `리플렉션`을 이용해 `Enum` 의 데이터를 가져와 `Swagger`
      의 `Example` 를 만드는 코드를 작성했습니다.

```java

@ApiErrorResponseExample(value = ServiceErrorCode.USER_NOT_FOUND)
@ApiErrorResponseExample(value = ServiceErrorCode.INVALID_CREDENTIALS)
@PostMapping("/login")
public ResponseEntity<LoginResponse> login(
    // ...
);
```

그렇게 각 API 를 문서화할 때 추가하고 싶은 예외 상황을 간편하게 관리할 수 있었습니다.
