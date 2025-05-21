package com.youserstack.toopa.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youserstack.toopa.domain.user.dto.UserResponse;
import com.youserstack.toopa.domain.user.dto.UserUpdateRequest;
import com.youserstack.toopa.domain.user.dto.UserCreateRequest;
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
  public String signup(UserCreateRequest request) {
    log.info("🟩 회원 등록 : {}", request);

    // 기존 회원 여부 확인
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("❌ 이미 가입된 이메일입니다.");
    }

    // 엔터티 생성
    UserEntity user = UserEntity.builder()
        .email(request.getEmail())
        .name(request.getName())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(UserRoleType.USER)
        .build();

    // 저장
    userRepository.save(user);
    log.info("🟩 회원 등록 : {}", user);

    return user.getEmail();
  }

  // ⬜ 회원 다건 조회
  public List<UserResponse> getAllUsers() {
    log.info("⬜ 회원 다건 조회");

    // 전체 유저 리스트 조회
    List<UserEntity> users = userRepository.findAll();

    // 전달객체 리스트 생성
    List<UserResponse> responses = new ArrayList<>();
    for (UserEntity user : users) {
      UserResponse userResponse = UserResponse.builder()
          .email(user.getEmail())
          .name(user.getName())
          .role(user.getRole().name())
          .build();
      responses.add(userResponse);
    }
    log.info("⬜ 회원 다건 조회 : {}", responses);

    return responses;
  }

  // ⬜ 회원 단건 조회
  public UserResponse getUser(String email) {
    log.info("⬜ 회원 단건 조회 : {}", email);

    // 이메일로 유저 정보 조회
    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 이메일입니다."));

    // 전달객체 생성
    UserResponse response = UserResponse.builder()
        .email(user.getEmail())
        .name(user.getName())
        .role(user.getRole().name())
        .build();
    log.info("⬜ 회원 단건 조회 : {}", response);

    return response;
  }

  // 🟨 회원 수정
  @Transactional
  public void updateUser(String email, UserUpdateRequest request) {
    log.info("🟨 회원 수정 : {}", request);

    // 쿼리 조회
    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 이메일입니다."));

    // 이름 업데이트 (필수 또는 선택에 따라)
    if (request.getName() != null && !request.getName().isBlank()) {
      user.updateUserInfo(request.getName(), null);
    }

    // 비밀번호 업데이트 (변경 요청시만)
    if (request.getPassword() != null && !request.getPassword().isBlank()) {
      user.updateUserInfo(null, passwordEncoder.encode(request.getPassword())); // 비밀번호는 꼭 해시처리
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
