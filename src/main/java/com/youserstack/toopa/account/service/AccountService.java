package com.youserstack.toopa.account.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.youserstack.toopa.account.dto.SignupRequest;
import com.youserstack.toopa.account.dto.AccountResponse;
import com.youserstack.toopa.account.dto.SigninRequest;
import com.youserstack.toopa.account.dto.SigninResponse;
import com.youserstack.toopa.account.entity.Account;
import com.youserstack.toopa.account.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  // 등록
  public void register(SignupRequest request) {
    log.info("{}", request);

    Account account = Account.builder()
        .email(request.getEmail())
        .name(request.getName())
        .password(passwordEncoder.encode(request.getPassword())) // 해시처리
        .role("USER")
        .build();

    accountRepository.save(account);
  }

  // 인증
  // 인가처리는 넥스트서버에서 토큰발급으로 처리
  public SigninResponse authenticate(SigninRequest request) {
    log.info("{}", request);

    Account account = accountRepository.findByEmail(request.getEmail())
        .orElse(null);
    if (account == null)
      return null;

    if (!passwordEncoder.matches(request.getPassword(), account.getPassword()))
      return null;

    return new SigninResponse(
        account.getEmail(),
        account.getName(),
        account.getRole());
  }

  // 조회
  public AccountResponse getAccountByEmail(String email) {
    log.info("email: {}", email);

    return accountRepository.findByEmail(email)
        .map(user -> new AccountResponse(
            user.getEmail(),
            user.getName(),
            user.getRole()))
        .orElse(null);
  }

}
