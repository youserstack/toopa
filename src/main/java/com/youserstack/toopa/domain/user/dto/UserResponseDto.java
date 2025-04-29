package com.youserstack.toopa.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

  private String email;
  private String name;
  private String role;

}
