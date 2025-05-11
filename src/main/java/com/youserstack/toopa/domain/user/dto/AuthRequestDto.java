package com.youserstack.toopa.domain.user.dto;

import lombok.Data;

@Data
public class AuthRequestDto {

  private String email;
  private String password;

}
