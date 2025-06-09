package com.haein.jwt.controller.exception;

import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {ServiceException.class})
  public ResponseEntity<CommonErrorResponse> handleServiceException(ServiceException e) {
    return ResponseEntity.status(e.getStatus())
        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        .body(CommonErrorResponse.of(e.getErrorCode()));
  }
}
