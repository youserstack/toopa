package com.youserstack.toopa.domain.user.controller;

import com.youserstack.toopa.domain.user.service.UserService;
import com.youserstack.toopa.domain.user.dto.UserDto;
import com.youserstack.toopa.domain.user.dto.UserUpdateDto;
import com.youserstack.toopa.domain.user.dto.SignupDto;
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
  public ResponseEntity<Void> signup(@RequestBody SignupDto dto) {
    String userEmail = userService.signup(dto);
    URI location = URI.create("/api/users/" + userEmail);
    return ResponseEntity.created(location).build();
  }

  // ⬜ 회원 다건 조회
  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers() {
    List<UserDto> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  // ⬜ 회원 단건 조회
  @GetMapping("/{email}")
  public ResponseEntity<UserDto> getUser(@PathVariable String email) {
    UserDto dto = userService.getUser(email);
    return ResponseEntity.ok(dto);
  }

  // 🟨 회원 수정
  @PutMapping("/{email}")
  public ResponseEntity<Void> updateUser(@PathVariable String email, @RequestBody @Valid UserUpdateDto dto) {
    userService.updateUser(email, dto);
    return ResponseEntity.noContent().build();
  }

  // 🟥 회원 삭제
  @DeleteMapping("/{email}")
  public ResponseEntity<Void> deleteUser(@PathVariable String email) {
    userService.deleteUser(email);
    return ResponseEntity.noContent().build();
  }

}
