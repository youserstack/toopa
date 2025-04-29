package com.youserstack.toopa.domain.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.youserstack.toopa.domain.user.dto.UserResponseDto;
import com.youserstack.toopa.domain.user.dto.SigninRequestDto;
import com.youserstack.toopa.domain.user.dto.SigninResponseDto;
import com.youserstack.toopa.domain.user.dto.SignupRequestDto;
import com.youserstack.toopa.domain.user.dto.UpdateRequestDto;
import com.youserstack.toopa.domain.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // 🟩 회원가입
  @PostMapping("/api/users")
  public ResponseEntity<?> signup(@RequestBody SignupRequestDto dto) {
    userService.createUser(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // 🟦 로그인
  @PostMapping("/api/auth/signin")
  public ResponseEntity<?> signin(@RequestBody SigninRequestDto dto) {
    SigninResponseDto response = userService.authenticate(dto);
    if (response == null)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    return ResponseEntity.ok(response);
  }

  // ⬜ 계정조회(단건)
  @GetMapping("/api/users")
  public ResponseEntity<?> getUser(@RequestParam String email) {
    UserResponseDto response = userService.getUser(email);
    if (response == null)
      return ResponseEntity.notFound().build();
    return ResponseEntity.ok(response);
  }

  // 🟨 계정수정
  @PutMapping("/api/users")
  public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateRequestDto dto, @RequestParam String email) {
    userService.updateUser(dto, email);
    return ResponseEntity.ok("회원정보 수정 완료");
  }

}
