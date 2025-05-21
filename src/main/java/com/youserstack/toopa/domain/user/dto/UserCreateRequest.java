package com.youserstack.toopa.domain.user.dto;

import lombok.Data;

@Data
public class UserCreateRequest {

  private String email;
  private String name;
  private String password;

}
