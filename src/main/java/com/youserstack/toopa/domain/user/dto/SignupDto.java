package com.youserstack.toopa.domain.user.dto;

import lombok.Data;

@Data
public class SignupDto {

  private String email;
  private String name;
  private String password;

}
