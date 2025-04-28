package com.youserstack.toopa.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youserstack.toopa.domain.user.dto.UserDto;
import com.youserstack.toopa.domain.user.dto.SigninRequestDto;
import com.youserstack.toopa.domain.user.dto.SigninResponseDto;
import com.youserstack.toopa.domain.user.dto.SignupRequestDto;
import com.youserstack.toopa.domain.user.entity.UserEntity;
import com.youserstack.toopa.domain.user.entity.UserRoleType;
import com.youserstack.toopa.domain.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // 회원가입
  @Transactional
  public void signup(SignupRequestDto request) {
    log.info("☑️ 회원가입 : {}", request);

    // 이미 등록된 이메일이 있는지 확인
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("❌ 이미 가입된 이메일입니다.");
    }

    // 새로운 유저 엔터티 생성
    UserEntity userEntity = UserEntity.builder()
        .email(request.getEmail())
        .name(request.getName())
        .password(passwordEncoder.encode(request.getPassword())) // 해시처리
        .role(UserRoleType.USER)
        .build();

    // 저장소에 저장
    userRepository.save(userEntity);
    log.info("🟢 회원가입 완료 {}", userEntity);

  }

  // 로그인
  // 인가처리는 넥스트서버에서 토큰발급으로 처리
  public SigninResponseDto signin(SigninRequestDto request) {
    log.info("☑️ 로그인 : {}", request);

    // 이메일로 유저 정보 조회
    UserEntity userEntity = userRepository.findByEmail(request.getEmail());
    if (userEntity == null) {
      throw new EntityNotFoundException("❌ 존재하지 않는 이메일입니다.");
    }

    // 비밀번호 일치 여부 확인
    if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
      throw new IllegalArgumentException("❌ 비밀번호가 일치하지 않습니다.");
    }

    // 유저 엔터티 데이터를 DTO에 담아서 반환
    SigninResponseDto signinResponseDto = new SigninResponseDto(
        userEntity.getEmail(),
        userEntity.getName(),
        userEntity.getRole().name());
    log.info("🟢 로그인 완료 {}", signinResponseDto);
    return signinResponseDto;
  }

  // 계정조회
  public UserDto getUserByEmail(String email) {
    log.info("☑️ 계정조회 : {}", email);

    // 이메일로 유저 정보 조회
    UserEntity userEntity = userRepository.findByEmail(email);
    if (userEntity == null) {
      throw new EntityNotFoundException("❌ 존재하지 않는 이메일입니다.");
    }

    // 유저 엔터티 데이터를 DTO에 담아서 반환
    UserDto userDto = new UserDto(
        userEntity.getEmail(),
        userEntity.getName(),
        userEntity.getRole().name());
    log.info("🟢 계정조회 : {}", userDto);
    return userDto;
  }

}
