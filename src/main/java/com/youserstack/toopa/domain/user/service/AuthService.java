package com.youserstack.toopa.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.youserstack.toopa.domain.user.dto.SigninDto;
import com.youserstack.toopa.domain.user.dto.UserDto;
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
  // 인가는 넥스트서버에서 토큰발급으로 처리
  public UserDto authenticate(SigninDto signinDto) {
    log.info("☑️ 회원 인증 : {}", signinDto);

    // 이메일로 유저 정보 조회
    UserEntity user = userRepository.findByEmail(signinDto.getEmail())
        .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 이메일입니다."));

    // 비밀번호 일치 여부 확인
    if (!passwordEncoder.matches(signinDto.getPassword(),
        user.getPassword())) {
      throw new IllegalArgumentException("❌ 비밀번호가 일치하지 않습니다.");
    }

    // 전달객체 생성
    UserDto userDto = UserDto.builder()
        .email(user.getEmail())
        .name(user.getName())
        .role(user.getRole().name())
        .build();
    log.info("🟢 회원 인증 {}", userDto);

    return userDto;
  }

}
