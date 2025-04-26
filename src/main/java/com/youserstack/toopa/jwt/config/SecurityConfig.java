package com.youserstack.toopa.jwt.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private DataSource dataSource;

  // 보안 필터 체인 설정 (HTTP 요청 보안 규칙 정의)
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/h2-console/**").permitAll() // H2 콘솔에 대한 접근 허용
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
        .headers(headers -> headers.frameOptions().sameOrigin());

    //
    ;

    return http.build();
  }

  // 비밀번호 암호화를 위한 메서드
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // 인증관리자 메서드
  // @Bean
  // public AuthenticationManager
  // authenticationManager(AuthenticationConfiguration
  // authenticationConfiguration)
  // throws Exception {
  // return authenticationConfiguration.getAuthenticationManager();
  // }

  // JDBC 기반 사용자 인증 설정
  // @Bean
  // public UserDetailsService userDetailsService() {
  // JdbcUserDetailsManager jdbcUserDetailsManager = new
  // JdbcUserDetailsManager(dataSource);

  // // 사용자인증쿼리
  // String sql1 = " SELECT username , password , enabled "
  // + " FROM user "
  // + "WHERE username = ? ";

  // // 사용자권한쿼리
  // String sql2 = " SELECT username , auth "
  // + " FROM user_auth "
  // + "WHERE username = ? ";

  // jdbcUserDetailsManager.setUsersByUsernameQuery(sql1);
  // jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(sql2);

  // return jdbcUserDetailsManager;
  // }

}
