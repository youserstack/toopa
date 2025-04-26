package com.youserstack.toopa.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // 모든 경로에 대해
        .allowedOrigins("http://localhost:3000") // 프론트 주소
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        // .allowedHeaders("*")
        .allowedHeaders("Authorization", "Content-Type") // <- 이 부분 중요
        .allowCredentials(true); // 쿠키, 인증 포함 시 true
  }

}
