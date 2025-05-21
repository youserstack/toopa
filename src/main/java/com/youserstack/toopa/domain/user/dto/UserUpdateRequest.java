package com.youserstack.toopa.domain.user.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {

  private String name;
  private String password;

}
