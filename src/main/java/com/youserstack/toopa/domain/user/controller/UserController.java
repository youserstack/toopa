package com.youserstack.toopa.domain.user.controller;

import com.youserstack.toopa.domain.user.service.UserService;
import com.youserstack.toopa.domain.user.dto.UserResponse;
import com.youserstack.toopa.domain.user.dto.UserUpdateRequest;
import com.youserstack.toopa.domain.user.dto.UserCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  // 🟩 회원 등록
  @PostMapping
  public ResponseEntity<Void> signup(@RequestBody UserCreateRequest request) {
    String userEmail = userService.signup(request);
    URI location = URI.create("/api/users/" + userEmail);
    return ResponseEntity.created(location).build();
  }

  // ⬜ 회원 다건 조회
  @GetMapping
  public ResponseEntity<List<UserResponse>> getAllUsers() {
    List<UserResponse> responses = userService.getAllUsers();
    return ResponseEntity.ok(responses);
  }

  // ⬜ 회원 단건 조회
  @GetMapping("/{email}")
  public ResponseEntity<UserResponse> getUser(@PathVariable String email) {
    UserResponse response = userService.getUser(email);
    return ResponseEntity.ok(response);
  }

  // 🟨 회원 수정
  @PutMapping("/{email}")
  public ResponseEntity<Void> updateUser(@PathVariable String email, @RequestBody @Valid UserUpdateRequest request) {
    userService.updateUser(email, request);
    return ResponseEntity.noContent().build();
  }

  // 🟥 회원 삭제
  @DeleteMapping("/{email}")
  public ResponseEntity<Void> deleteUser(@PathVariable String email) {
    userService.deleteUser(email);
    return ResponseEntity.noContent().build();
  }

}
