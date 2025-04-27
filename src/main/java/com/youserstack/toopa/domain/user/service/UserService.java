package com.youserstack.toopa.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.youserstack.toopa.domain.user.dto.UserDto;
import com.youserstack.toopa.domain.user.dto.SigninRequestDto;
import com.youserstack.toopa.domain.user.dto.SigninResponseDto;
import com.youserstack.toopa.domain.user.dto.SignupRequestDto;
import com.youserstack.toopa.domain.user.entity.UserEntity;
import com.youserstack.toopa.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // 등록
  public void register(SignupRequestDto request) {
    log.info("{}", request);

    UserEntity userEntity = UserEntity.builder()
        .email(request.getEmail())
        .name(request.getName())
        .password(passwordEncoder.encode(request.getPassword())) // 해시처리
        .role("USER")
        .build();

    userRepository.save(userEntity);
  }

  // 인증
  // 인가처리는 넥스트서버에서 토큰발급으로 처리
  public SigninResponseDto authenticate(SigninRequestDto request) {
    log.info("{}", request);

    UserEntity userEntity = userRepository.findByEmail(request.getEmail())
        .orElse(null);
    if (userEntity == null)
      return null;

    if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword()))
      return null;

    return new SigninResponseDto(
        userEntity.getEmail(),
        userEntity.getName(),
        userEntity.getRole());
  }

  // 조회
  public UserDto getUserByEmail(String email) {
    log.info("email: {}", email);

    return userRepository.findByEmail(email)
        .map(user -> new UserDto(
            user.getEmail(),
            user.getName(),
            user.getRole()))
        .orElse(null);
  }

}
