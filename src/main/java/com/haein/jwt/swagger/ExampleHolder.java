package com.haein.jwt.swagger;

import io.swagger.v3.oas.models.examples.Example;
import lombok.Builder;

@Builder
public record ExampleHolder(
    Integer statusCode,
    String errorCode,
    String errorMessage,
    Example holder
) {

}
