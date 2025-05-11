package com.youserstack.toopa.domain.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.youserstack.toopa.domain.user.dto.UserResponseDto;
import com.youserstack.toopa.domain.user.dto.SignupRequestDto;
import com.youserstack.toopa.domain.user.dto.UpdateRequestDto;
import com.youserstack.toopa.domain.user.service.UserService;

import jakarta.validation.Valid;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // 🟩 회원 생성
  @PostMapping
  public ResponseEntity<?> signup(@RequestBody SignupRequestDto dto) {
    userService.createUser(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // ⬜ 회원 단건 조회
  @GetMapping
  public ResponseEntity<?> getUser(@RequestParam String email) {
    UserResponseDto response = userService.getUser(email);
    if (response == null)
      return ResponseEntity.notFound().build();
    return ResponseEntity.ok(response);
  }

  // 🟨 회원 수정
  @PutMapping
  public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateRequestDto dto, @RequestParam String email) {
    userService.updateUser(dto, email);
    return ResponseEntity.ok("회원정보 수정 완료");
  }

}
