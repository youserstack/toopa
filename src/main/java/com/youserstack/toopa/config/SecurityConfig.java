package com.youserstack.toopa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.youserstack.toopa.domain.user.entity.UserRoleType;

// import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // 보안필터체인 설정 (HTTP요청 보안규칙 정의)
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        // 접근권한 인가설정
        .authorizeHttpRequests(auth -> auth
            // .requestMatchers("/h2-console/**").permitAll() // H2 콘솔에 대한 접근 허용
            .requestMatchers("/**").permitAll() // 모든 요청을 허용
            .anyRequest().authenticated() // 나머지 요청은 인증 필요
        )

        // http 기본인증 비활성화
        .httpBasic(basic -> basic.disable())

        // 폼 로그인 비활성화
        .formLogin(login -> login.disable())

        // CSRF 보호기능 비활성화
        .csrf(csrf -> csrf.disable())

    // 세션 정책 설정
    // 세션 사용하지않고, jwt 사용하기때문에, 세션 불필요
    // .sessionManagement(management ->
    // management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    // X-Frame-Options
    // .headers(headers -> headers.frameOptions().sameOrigin());

    //
    ;

    return http.build();
  }

  // 비밀번호 암호화
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // RoleHierarchy를 설정하는 메서드
  public RoleHierarchy roleHierarchy() {
    // RoleHierarchyImpl를 사용하여 ROLE_ 접두어를 포함하는 역할 계층을 설정
    return RoleHierarchyImpl.withRolePrefix("ROLE_")
        // "ADMIN" 역할은 "USER" 역할을 포함(즉, ADMIN은 USER의 권한을 자동으로 포함)
        .role(UserRoleType.ADMIN.toString()).implies(UserRoleType.USER.toString())
        // 설정을 마친 후 RoleHierarchy 객체를 빌드하여 반환
        .build();
  }

}
