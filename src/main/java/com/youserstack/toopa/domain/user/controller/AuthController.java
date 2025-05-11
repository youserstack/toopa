package com.youserstack.toopa.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youserstack.toopa.domain.user.dto.SigninRequestDto;
import com.youserstack.toopa.domain.user.dto.SigninResponseDto;
import com.youserstack.toopa.domain.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth/signin")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  // 🟦 회원 로그인
  @PostMapping
  public ResponseEntity<?> signin(@RequestBody SigninRequestDto dto) {
    SigninResponseDto response = authService.authenticate(dto);
    if (response == null)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    return ResponseEntity.ok(response);
  }

}
