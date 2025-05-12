package com.youserstack.toopa.domain.user.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.youserstack.toopa.domain.user.entity.UserEntity;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  // UserEntity findByEmail(String email);
  Optional<UserEntity> findByEmail(String email);

  boolean existsByEmail(String email); // 이메일 존재 여부 체크
}
