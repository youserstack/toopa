package com.youserstack.toopa.domain.user.dto;

import lombok.Data;

@Data
public class SigninRequestDto {

  private String email;
  private String password;

}
