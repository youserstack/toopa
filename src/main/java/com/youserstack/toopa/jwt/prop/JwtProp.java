package com.youserstack.toopa.jwt.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("com.youserstack.toopa")
public class JwtProp {

  // com.youserstack.toopa.secret-key -> secretKey : {인코딩된 시크릿키}
  private String secretKey;

}
