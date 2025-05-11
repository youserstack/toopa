package com.youserstack.toopa.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

  private String email;
  private String name;
  private String role;

}
