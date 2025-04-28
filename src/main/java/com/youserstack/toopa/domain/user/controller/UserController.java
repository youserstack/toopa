package com.youserstack.toopa.domain.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.youserstack.toopa.domain.user.dto.UserDto;
import com.youserstack.toopa.domain.user.dto.SigninRequestDto;
import com.youserstack.toopa.domain.user.dto.SigninResponseDto;
import com.youserstack.toopa.domain.user.dto.SignupRequestDto;
import com.youserstack.toopa.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // 회원가입
  @PostMapping("/api/signup")
  public ResponseEntity<Void> signup(@RequestBody SignupRequestDto request) {
    userService.signup(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // 로그인
  @PostMapping("/api/signin")
  public ResponseEntity<?> signin(@RequestBody SigninRequestDto request) {
    SigninResponseDto response = userService.signin(request);
    if (response == null)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    return ResponseEntity.ok(response);
  }

  // 계정조회
  @GetMapping("/api/user")
  public ResponseEntity<UserDto> getUser(@RequestParam String email) {
    UserDto response = userService.getUserByEmail(email);
    if (response == null)
      return ResponseEntity.notFound().build();
    return ResponseEntity.ok(response);
  }

}
