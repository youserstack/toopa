package com.youserstack.toopa.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youserstack.toopa.domain.user.dto.LoginRequest;
import com.youserstack.toopa.domain.user.dto.UserResponse;
import com.youserstack.toopa.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/signin")
public class AuthController {

  private final AuthService authService;

  // 🟦 회원 인증
  @PostMapping
  public ResponseEntity<UserResponse> signin(@RequestBody LoginRequest request) {
    UserResponse response = authService.authenticate(request);
    return ResponseEntity.ok(response);
  }

}
