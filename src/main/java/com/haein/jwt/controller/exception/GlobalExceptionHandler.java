package com.haein.jwt.controller.exception;

import com.haein.jwt.security.JwtException;
import com.haein.jwt.service.exception.ServiceException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {ServiceException.class})
  public ResponseEntity<CommonErrorResponse> handleServiceException(ServiceException e) {
    return ResponseEntity.status(e.getStatus())
        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        .body(CommonErrorResponse.of(e.getServiceErrorCode()));
  }

  @ExceptionHandler(value = {JwtException.class})
  public ResponseEntity<CommonErrorResponse> handleJwtException(JwtException e) {
    return ResponseEntity.status(e.getStatus())
        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        .body(CommonErrorResponse.of(e.getCode()));
  }

  @ExceptionHandler(value = {AccessDeniedException.class})
  public ResponseEntity<CommonErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        .body(CommonErrorResponse.securityErrorResponse());
  }
}
