package com.youserstack.toopa.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.youserstack.toopa.domain.user.dto.SigninRequestDto;
import com.youserstack.toopa.domain.user.dto.SigninResponseDto;
import com.youserstack.toopa.domain.user.entity.UserEntity;
import com.youserstack.toopa.domain.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // 🟦 회원 인증
  // 인가처리는 넥스트서버에서 토큰발급으로 처리
  public SigninResponseDto authenticate(SigninRequestDto signinRequestDto) {
    log.info("youserstack");
    log.info("☑️ 로그인 : {}", signinRequestDto);

    // 이메일로 유저 정보 조회
    UserEntity userEntity = userRepository.findByEmail(signinRequestDto.getEmail())
        .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 이메일입니다."));

    // 비밀번호 일치 여부 확인
    if (!passwordEncoder.matches(signinRequestDto.getPassword(),
        userEntity.getPassword())) {
      throw new IllegalArgumentException("❌ 비밀번호가 일치하지 않습니다.");
    }

    // 전달객체 생성
    SigninResponseDto signinResponseDto = SigninResponseDto.builder()
        .email(userEntity.getEmail())
        .name(userEntity.getName())
        .role(userEntity.getRole().name())
        .build();
    log.info("🟢 로그인 완료 {}", signinResponseDto);

    return signinResponseDto;
  }

}
