package com.haein.jwt.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Spring Boot 를 이용한 JWT 인증 & 인가 API",
        description = "JWT 를 사용하여 애플리케이션 인증 & 인가 서비스와 API를 구현한 프로젝트입니다.  " + "\n"
            + "회원가입 후 로그인하면 JWT Token 이 발급되고 오른쪽 Authorize 에 토큰을 입력해서 API를 테스트하면 됩니다.  " + "\n"
            + "Bearer 제외하고 뒤에 토큰부분만 복사해서 사용하시면 됩니다  " + "\n"
            + "관리자로 테스트하려면 로그인 Example 에 있는 관리자 아이디로 로그인을 진행하면 됩니다.",
        version = "v1"
    ))
@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    SecurityScheme securityScheme = new SecurityScheme()
        .type(Type.HTTP).scheme("bearer").bearerFormat("JWT")
        .in(In.HEADER).name("Authorization");
    SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
        .security(Arrays.asList(securityRequirement));
  }

}
