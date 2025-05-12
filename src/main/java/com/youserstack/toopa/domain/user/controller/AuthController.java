package com.youserstack.toopa.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youserstack.toopa.domain.user.dto.SigninDto;
import com.youserstack.toopa.domain.user.dto.UserDto;
import com.youserstack.toopa.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/signin")
public class AuthController {

  private final AuthService authService;

  // 🟦 회원 인증
  @PostMapping
  public ResponseEntity<UserDto> signin(@RequestBody SigninDto dto) {
    UserDto userDto = authService.authenticate(dto);
    return ResponseEntity.ok(userDto);
  }

}
