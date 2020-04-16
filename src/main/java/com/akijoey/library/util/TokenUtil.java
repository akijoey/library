package com.akijoey.library.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    private static final String ISSUER = "akijoey";
    private static final String SECRET = "library";

    private static final long EXPIRATION_ACCESS = 3600L;    // 1h = 3600s
    private static final long EXPIRATION_REFRESH = 604800L; // 7d = 604800s

    public static String generateToken(String subject) throws JsonProcessingException {
        return JwtHelper.encode(new ObjectMapper().writeValueAsString(new HashMap<>(){{
            put("iss", ISSUER);
            put("sub", subject);
            put("exp", Long.toString(System.currentTimeMillis() + EXPIRATION_ACCESS * 1000));
        }}), new MacSigner(SECRET)).getEncoded();
    }

    private static Map<String, String> parseToken(String token) throws JsonProcessingException {
        String claims = JwtHelper.decodeAndVerify(token, new MacSigner(SECRET)).getClaims();
        return new ObjectMapper().readValue(claims, Map.class);
    }

    public static String getSubject(String token) throws JsonProcessingException {
        return parseToken(token).get("sub");
    }

    public static boolean isExpiration(String token) throws JsonProcessingException {
        return Long.parseLong(parseToken(token).get("exp")) < System.currentTimeMillis();
    }

}
