package com.youserstack.toopa.domain.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youserstack.toopa.domain.user.dto.UserReadDto;
import com.youserstack.toopa.domain.user.dto.UserCreateDto;
import com.youserstack.toopa.domain.user.dto.UserUpdateDto;
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

  // 🟩 회원 등록
  @Transactional // CUD에 포함하면 롤백이 필요할수있기때문에 이 어노테이션을 추가
  public String createUser(UserCreateDto dto) {
    log.info("☑️ 회원 등록 : {}", dto);

    // 이미 등록된 이메일이 있는지 확인
    if (userRepository.existsByEmail(dto.getEmail())) {
      throw new IllegalArgumentException("❌ 이미 가입된 이메일입니다.");
    }

    // 새로운 유저 엔터티 생성
    UserEntity user = new UserEntity();
    user.setEmail(dto.getEmail());
    user.setName(dto.getName());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setRole(UserRoleType.USER);

    // 저장
    userRepository.save(user);
    log.info("🟢 회원 등록 {}", user);

    return user.getEmail();
  }

  // ⬜ 회원 다건 조회
  public List<UserReadDto> getAllUsers() {
    log.info("☑️ 회원 다건 조회");

    // 전체 유저 리스트 조회
    List<UserEntity> users = userRepository.findAll();

    // 전달객체 리스트 생성
    List<UserReadDto> dtos = new ArrayList<>();
    for (UserEntity user : users) {
      UserReadDto dto = UserReadDto.builder()
          .email(user.getEmail()).name(user.getName()).role(user.getRole().name()).build();
      dtos.add(dto);
    }

    return dtos;
  }

  // ⬜ 회원 단건 조회
  public UserReadDto getUser(String email) {
    log.info("☑️ 회원 단건 조회 : {}", email);

    // 이메일로 유저 정보 조회
    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 이메일입니다."));

    // 전달객체 생성
    UserReadDto dto = UserReadDto.builder()
        .email(userEntity.getEmail())
        .name(userEntity.getName())
        .role(userEntity.getRole().name())
        .build();
    log.info("🟢 회원 단건 조회 : {}", dto);

    return dto;
  }

  // 🟨 회원 수정
  @Transactional
  public void updateUser(UserUpdateDto dto, String email) {
    log.info("☑️ 회원 수정 : {}", dto);

    // 이메일로 유저 조회
    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 이메일입니다."));

    // 이름 업데이트 (필수 또는 선택에 따라)
    if (dto.getName() != null && !dto.getName().isBlank()) {
      userEntity.updateUserInfo(dto.getName(), null);
    }

    // 비밀번호 업데이트 (변경 요청시만)
    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
      userEntity.updateUserInfo(null, passwordEncoder.encode(dto.getPassword())); // 비밀번호는 꼭 해시처리
    }

    // 저장
    userRepository.save(userEntity);
    log.info("🟢 회원 수정 : {}", userEntity);
  }

  // 🟥 회원 삭제
  @Transactional
  public void deleteUser(String email) {
    log.info("☑️ 회원 삭제 : {}", email);

    // 이메일로 유저 조회
    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 이메일입니다."));

    // 삭제
    userRepository.delete(userEntity);
    log.info("🟢 회원 삭제 : {}", email);
  }

}
