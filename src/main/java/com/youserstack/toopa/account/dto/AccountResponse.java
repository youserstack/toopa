package com.youserstack.toopa.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {
  private String email;
  private String name;
  private String role;
}
