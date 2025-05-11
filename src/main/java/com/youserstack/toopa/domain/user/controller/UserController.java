package com.youserstack.toopa.domain.user.controller;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.youserstack.toopa.domain.user.dto.UserReadDto;
import com.youserstack.toopa.domain.user.dto.UserCreateDto;
import com.youserstack.toopa.domain.user.dto.UserUpdateDto;
import com.youserstack.toopa.domain.user.service.UserService;

import jakarta.validation.Valid;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // 🟩 회원 생성
  @PostMapping
  public ResponseEntity<Void> signup(@RequestBody UserCreateDto dto) {
    String userEmail = userService.createUser(dto);
    URI location = URI.create("/api/users/" + userEmail);
    return ResponseEntity.created(location).build();
  }

  // ⬜ 회원 다건 조회
  @GetMapping
  public ResponseEntity<List<UserReadDto>> getAllUsers() {
    List<UserReadDto> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  // ⬜ 회원 단건 조회
  @GetMapping("/{email}")
  public ResponseEntity<UserReadDto> getUser(@PathVariable String email) {
    UserReadDto dto = userService.getUser(email);
    return ResponseEntity.ok(dto);
  }

  // 🟨 회원 수정
  @PutMapping("/{email}")
  public ResponseEntity<Void> updateUser(@RequestBody @Valid UserUpdateDto dto, @PathVariable String email) {
    userService.updateUser(dto, email);
    return ResponseEntity.noContent().build();
  }

  // 🟥 회원 삭제
  @DeleteMapping("/{email}")
  public ResponseEntity<Void> deleteUser(@PathVariable String email) {
    userService.deleteUser(email);
    return ResponseEntity.noContent().build();
  }

}
