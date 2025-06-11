package com.haein.jwt.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER = "Authorization";

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;
  private final HandlerExceptionResolver resolver;

  public JwtAuthenticationFilter(JwtUtil jwtUtil,
      UserDetailsService userDetailsService,
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
    this.resolver = resolver;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String tokenValue = request.getHeader(AUTHORIZATION_HEADER);

      if (tokenValue == null) {
        throw JwtException.from(SecurityErrorCode.MISSING_HEADER);
      }

      jwtUtil.validateToken(tokenValue);
      Claims claims = jwtUtil.parseToken(tokenValue);

      var username = claims.getSubject();
      UserDetails info = userDetailsService.loadUserByUsername(username);

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          username, null, info.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    } catch (JwtException e) {
      resolver.resolveException(request, response, null, e);
      log.error("JWT 인증 실패");
      return;
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return path.startsWith("/api/v1/users/signup") || path.startsWith("/api/v1/users/login");
  }
}
