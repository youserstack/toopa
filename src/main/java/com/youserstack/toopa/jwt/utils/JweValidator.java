package com.youserstack.toopa.jwt.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

import java.util.Base64;

public class JweValidator {

    private static final String SECRET = "ZHMcAcjwedN+6EBu64tiubLQl5Vh9V+qQJ3BHlZrgvEN4G+rVTCXFxGoYw55X/LdgVfSD4MD3lnB6DewsPiS4Q==";

    public static JWTClaimsSet validate(String token) throws Exception {
        // JWE 파싱
        EncryptedJWT jwt = EncryptedJWT.parse(token);

        // 복호화 키 설정 (Direct 방식)
        JWEDecrypter decrypter = new DirectDecrypter(Base64.getDecoder().decode(SECRET));
        jwt.decrypt(decrypter); // 복호화 실행

        // 복호화된 Claim 가져오기
        return jwt.getJWTClaimsSet();
    }

}

// package com.youserstack.toopa.jwt.utils;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;

// import java.security.Key;
// import java.util.Date;

// public class JwtValidation {
// private static final String SECRET =
// "xD8ZuzMTD7IapdmNO3XQdtjQAx1n4SoVUJCcP6b6aoE="; // 256비트 이상
// // private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

// public static Claims validateToken(String token) throws JwtException {
// return Jwts.parserBuilder()
// .setSigningKey(SECRET.getBytes())
// .build()
// .parseClaimsJws(token)
// .getBody();
// }
// }
