package com.haein.jwt.swagger;

import com.haein.jwt.service.exception.ServiceErrorCode;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ApiErrorResponseExamples.class)
public @interface ApiErrorResponseExample {

  ServiceErrorCode value();
}
