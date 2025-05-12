package com.youserstack.toopa.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youserstack.toopa.domain.user.dto.UserDto;
import com.youserstack.toopa.domain.user.dto.UserUpdateDto;
import com.youserstack.toopa.domain.user.dto.SignupDto;
import com.youserstack.toopa.domain.user.entity.UserEntity;
import com.youserstack.toopa.domain.user.entity.UserRoleType;
import com.youserstack.toopa.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // 🟩 회원 등록
  @Transactional // CUD에 포함하면 롤백이 필요할수있기때문에 이 어노테이션을 추가
  public String signup(SignupDto dto) {
    log.info("🟩 회원 등록 : dto={}", dto);

    // 기존 회원 여부 확인
    if (userRepository.existsByEmail(dto.getEmail())) {
      throw new IllegalArgumentException("❌ 이미 가입된 이메일입니다.");
    }

    // 엔터티 생성
    UserEntity user = UserEntity.builder()
        .email(dto.getEmail())
        .name(dto.getName())
        .password(dto.getPassword())
        .role(UserRoleType.USER)
        .build();

    // 저장
    userRepository.save(user);
    log.info("🟩 회원 등록 : user={}", user);

    return user.getEmail();
  }

  // ⬜ 회원 다건 조회
  public List<UserDto> getAllUsers() {
    log.info("⬜ 회원 다건 조회");

    // 전체 유저 리스트 조회
    List<UserEntity> users = userRepository.findAll();

    // 전달객체 리스트 생성
    List<UserDto> dtos = new ArrayList<>();
    for (UserEntity user : users) {
      UserDto dto = UserDto.builder()
          .email(user.getEmail())
          .name(user.getName())
          .role(user.getRole().name())
          .build();
      dtos.add(dto);
    }
    log.info("⬜ 회원 다건 조회 : {}", dtos);

    return dtos;
  }

  // ⬜ 회원 단건 조회
  public UserDto getUser(String email) {
    log.info("⬜ 회원 단건 조회 : {}", email);

    // 이메일로 유저 정보 조회
    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 이메일입니다."));

    // 전달객체 생성
    UserDto dto = UserDto.builder()
        .email(user.getEmail())
        .name(user.getName())
        .role(user.getRole().name())
        .build();
    log.info("⬜ 회원 단건 조회 : {}", dto);

    return dto;
  }

  // 🟨 회원 수정
  @Transactional
  public void updateUser(String email, UserUpdateDto dto) {
    log.info("🟨 회원 수정 : {}", dto);

    // 쿼리 조회
    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 이메일입니다."));

    // 이름 업데이트 (필수 또는 선택에 따라)
    if (dto.getName() != null && !dto.getName().isBlank()) {
      user.updateUserInfo(dto.getName(), null);
    }

    // 비밀번호 업데이트 (변경 요청시만)
    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
      user.updateUserInfo(null, passwordEncoder.encode(dto.getPassword())); // 비밀번호는 꼭 해시처리
    }

    // 저장
    userRepository.save(user);
    log.info("🟨 회원 수정 : {}", user);
  }

  // 🟥 회원 삭제
  @Transactional
  public void deleteUser(String email) {
    log.info("🟥 회원 삭제 : {}", email);

    // 쿼리 조회
    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 이메일입니다."));

    // 삭제
    userRepository.delete(user);
    log.info("🟥 회원 삭제 : {}", email);
  }

}
