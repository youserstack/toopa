package com.youserstack.toopa.jwt.controller;

import org.springframework.web.bind.annotation.RestController;

import com.youserstack.toopa.jwt.domain.AuthenticationRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginController {

  // @PostMapping("login")
  // public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {

  // String username = request.getUsername();
  // String password = request.getPassword();

  // System.out.println("username" + username);
  // System.out.println("password" + password);

  // // 사용자권한
  // List<String> roles = new ArrayList<>();
  // roles.add("ROLE_USER");
  // roles.add("ROLE_ADMIN");

  // }

}
