package com.youserstack.toopa.home;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

  @GetMapping
  public String home(@AuthenticationPrincipal User user, Model model) throws Exception {
    log.info("GET /");

    // if (user != null) {
    // String username = user.getUsername();
    // model.addAttribute("user", user);
    // // Users user = userService.select(username);
    // log.info("인증 사용자 : {}", username);
    // } else {
    // log.warn("미인증 사용자");
    // // 필요시 로그인 페이지로 리다이렉트하거나 기본값 설정
    // }

    return "index";
  }

}
