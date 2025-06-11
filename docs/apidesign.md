# API 스펙

## 기능

### 1. 회원가입

#### 요청

```http request
POST /signup
```

#### 응답

정상 요청

```http
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "username": "{userName}",
  "password": "{password}",
  "nickname": "{nickname}"
}
```

이미 가입된 사용자인 경우

```http
HTTP/1.1 409 CONFLICT
Content-Type: application/json;charset=UTF-8

{
  "error": {
    "code": "USER_ALREADY_EXISTS",
    "message": "이미 가입된 사용자입니다."
  }
}
```

### 2. 로그인

#### 요청

```http request
POST /login
Content-Type: application/json;charset=UTF-8

{
  "username": "{username",
  "password": "{password}"
}
```

#### 응답

정상

```http request
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "token": "{token}"
}
```

잘못된 로그인 정보인 경우

```http
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "error": {
    "code": "INVALID_CREDENTIALS",
    "message": "아이디 또는 비밀번호가 올바르지 않습니다."
  }
}
```

### 3. 관리자 권한 부여

#### 요청

```http request
PATCH /admin/users/{userId}/roles
Content-Type: application/json;charset=UTF-8
```

#### 응답

정상

```http request
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "username": "JIN HO",
  "nickname": "Mentos",
  "roles": [
    {
      "role": "Admin"
    }
  ]
}
```

권한이 없는 경우

```http
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "error": {
    "code": "ACCESS_DENIED",
    "message": "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."
  }
}
```

#### JWT 에러 처리

보호하는 API 요청은 모두 Token 을 검증하고 유효하지 않은 경우 다음 응답을 반환한다

```http
{
  "error": {
    "code": "INVALID_TOKEN",
    "message": "유효하지 않은 인증 토큰입니다."
  }
}
```

```http request
{
  "error": {
    "code": "ACCESS_DENIED",
    "message": "접근 권한이 없습니다."
  }
}
```
