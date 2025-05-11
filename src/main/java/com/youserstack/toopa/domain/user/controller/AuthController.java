package com.youserstack.toopa.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youserstack.toopa.domain.user.dto.AuthRequestDto;
import com.youserstack.toopa.domain.user.dto.AuthResponseDto;
import com.youserstack.toopa.domain.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth/signin")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  // 🟦 회원 인증
  @PostMapping
  public ResponseEntity<AuthResponseDto> signin(@RequestBody AuthRequestDto dto) {
    AuthResponseDto response = authService.authenticate(dto);
    return ResponseEntity.ok(response);
  }

}
