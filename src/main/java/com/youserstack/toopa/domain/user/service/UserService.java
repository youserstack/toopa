package com.youserstack.toopa.domain.user.service;

import java.util.ArrayList;
import java.util.List;

// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youserstack.toopa.domain.user.dto.UserResponseDto;
import com.youserstack.toopa.domain.user.dto.SigninRequestDto;
import com.youserstack.toopa.domain.user.dto.SigninResponseDto;
import com.youserstack.toopa.domain.user.dto.SignupRequestDto;
import com.youserstack.toopa.domain.user.dto.UpdateRequestDto;
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

  // 🟩 회원생성 - CREATE
  @Transactional // CUD에 포함하면 롤백이 필요할수있기때문에 이 어노테이션을 추가
  public void createUser(SignupRequestDto dto) {

    log.info("☑️ 회원가입 : {}", dto);

    // 이미 등록된 이메일이 있는지 확인
    if (userRepository.existsByEmail(dto.getEmail())) {
      throw new IllegalArgumentException("❌ 이미 가입된 이메일입니다.");
    }

    // 새로운 유저 엔터티 생성
    UserEntity user = UserEntity.builder()
        .email(dto.getEmail())
        .name(dto.getName())
        .password(passwordEncoder.encode(dto.getPassword())) // 해시처리
        .role(UserRoleType.USER)
        .build();

    // 저장
    userRepository.save(user);
    log.info("🟢 회원가입 완료 {}", user);
  }

  // 🟦 회원인증 - CRUD에 포함되지않음
  // 인가처리는 넥스트서버에서 토큰발급으로 처리
  public SigninResponseDto authenticate(SigninRequestDto dto) {

    log.info("☑️ 로그인 : {}", dto);

    // 이메일로 유저 정보 조회
    UserEntity user = userRepository.findByEmail(dto.getEmail());
    if (user == null) {
      throw new EntityNotFoundException("❌ 존재하지 않는 이메일입니다.");
    }

    // 비밀번호 일치 여부 확인
    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("❌ 비밀번호가 일치하지 않습니다.");
    }

    // 유저 엔터티 데이터를 DTO에 담아서 반환
    SigninResponseDto signinResponseDto = new SigninResponseDto(
        user.getEmail(),
        user.getName(),
        user.getRole().name());
    log.info("🟢 로그인 완료 {}", signinResponseDto);
    return signinResponseDto;
  }

  // ⬜ 회원조회(단건) - READ
  public UserResponseDto getUser(String email) {

    log.info("☑️ 단건 계정조회 : {}", email);

    // 이메일로 유저 정보 조회
    UserEntity user = userRepository.findByEmail(email);
    if (user == null) {
      throw new EntityNotFoundException("❌ 존재하지 않는 이메일입니다.");
    }

    // 유저 엔터티 데이터를 DTO에 담아서 반환
    UserResponseDto userResponseDto = new UserResponseDto(
        user.getEmail(),
        user.getName(),
        user.getRole().name());
    log.info("🟢 계정조회 : {}", userResponseDto);
    return userResponseDto;
  }

  // ⬜ 회원조회(다건) - READ
  public List<UserResponseDto> getUsers() {

    log.info("☑️ 다건 계정조회");

    // 전체 유저 리스트 조회
    List<UserEntity> users = userRepository.findAll();

    // 유저 리스트를 DTO 리스트에 담아서 반환
    List<UserResponseDto> dtos = new ArrayList<>();
    for (UserEntity user : users) {
      UserResponseDto dto = new UserResponseDto(user.getEmail(), user.getName(), user.getRole().toString());
      dtos.add(dto);
    }
    return dtos;
  }

  // 🟨 회원수정 - UPDATE
  @Transactional
  public void updateUser(UpdateRequestDto dto, String email) {

    log.info("☑️ 회원정보수정 요청 : {}", dto);

    // 이메일로 유저 조회
    UserEntity user = userRepository.findByEmail(email);
    if (user == null) {
      throw new EntityNotFoundException("❌ 존재하지 않는 이메일입니다: " + email);
    }

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
    log.info("🟢 회원정보수정 완료 : {}", user);
  }

  // 🟥 회원삭제 - DELETE
  @Transactional
  public void deleteUser(String email) {
    log.info("☑️ 회원정보삭제 요청 : {}", email);

    // 이메일로 유저 조회
    UserEntity user = userRepository.findByEmail(email);
    if (user == null) {
      throw new EntityNotFoundException("❌ 존재하지 않는 이메일입니다: " + email);
    }

    // 삭제
    userRepository.delete(user);
    log.info("🟢 회원정보삭제 완료 : {}", email);
  }

}

// ⬜ 회원인증(스프링시큐리티) - READ
// @Override
// public UserDetails loadUserByUsername(String username) {

// // 이메일로 유저 조회
// UserEntity user = userRepository.findByUsername(username);
// if (user == null) {
// throw new UsernameNotFoundException("존재하지 않는 사용자입니다: " + username);
// }

// // 스피링시큐리티 유저빌더패턴으로 반환
// return User.builder()
// .username(user.getEmail()) // 스프링시큐리티에서 유저네임은 로그인식별자로 이메일로 정하여 맵핑함
// .password(user.getPassword())
// .roles(user.getRole().toString())
// .build();
// }

// 회원접근권한체크
// public Boolean isAccess(String username) {

// // 현재 로그인 되어 있는 유저의 username
// String sessionUsername =
// SecurityContextHolder.getContext().getAuthentication().getName();
// // 현재 로그인 되어 있는 유저의 role
// String sessionRole =
// SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next()
// .getAuthority();

// // ADMIN 접근허용
// if ("ROLE_ADMIN".equals(sessionRole))
// return true;

// // 수평적으로 특정 행위를 수행할 username에 대해 세션(현재 로그인한) username과 같은지
// if (username.equals(sessionUsername))
// return true;

// // 접근제한
// return false;
// }
