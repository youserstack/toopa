package com.youserstack.toopa.account.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.youserstack.toopa.account.dto.SignupRequest;
import com.youserstack.toopa.account.dto.AccountResponse;
import com.youserstack.toopa.account.dto.SigninRequest;
import com.youserstack.toopa.account.dto.SigninResponse;
import com.youserstack.toopa.account.service.AccountService;

@RestController
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  // 회원가입
  @PostMapping("/api/signup")
  public ResponseEntity<Void> signup(@RequestBody SignupRequest request) {

    // 등록처리
    accountService.register(request);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // 로그인
  @PostMapping("/api/signin")
  public ResponseEntity<?> signin(@RequestBody SigninRequest request) {

    // 인증처리 (인가처리는 넥스트서버에서 처리)
    SigninResponse response = accountService.authenticate(request);
    if (response == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(response);
  }

  // 계정조회
  @GetMapping("/api/account")
  public ResponseEntity<AccountResponse> getUserData(@RequestParam String email) {

    // 계정조회처리
    AccountResponse response = accountService.getAccountByEmail(email);
    if (response == null) {
      return ResponseEntity.notFound().build();
    }

    // 응답처리
    return ResponseEntity.ok(response);
  }

}
