package com.haein.jwt.service.dto.request;

public record LoginRequestDto(
    String username,
    String password
) {
}
