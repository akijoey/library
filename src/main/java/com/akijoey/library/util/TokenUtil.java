package com.akijoey.library.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    private static final String ISSUER = "akijoey";
    private static final String SECRET = "library";

    private static final long EXPIRATION_ACCESS = 3600L;    // 1h = 3600s
    private static final long EXPIRATION_REFRESH = 7200L;   // 7d = 604800s

    @Autowired
    RedisUtil redisUtil;

    // generate token
    public String generateToken(String subject) throws JsonProcessingException {
        String expiration = Long.toString(System.currentTimeMillis() + EXPIRATION_ACCESS * 1000);
        String token = JwtHelper.encode(new ObjectMapper().writeValueAsString(new HashMap<>(){{
            put("iss", ISSUER);
            put("sub", subject);
            put("exp", expiration);
        }}), new MacSigner(SECRET)).getEncoded();
        if (redisUtil.hasKey(subject)) {
            redisUtil.delete(subject);
        }
        redisUtil.set(subject, expiration, EXPIRATION_REFRESH);
        return token;
    }

    // parse token
    public Map<String, String> parseToken(String token) throws JsonProcessingException {
        String claims = JwtHelper.decodeAndVerify(token, new MacSigner(SECRET)).getClaims();
        return new ObjectMapper().readValue(claims, Map.class);
    }

    // get subject
    public String getSubject(String token) throws JsonProcessingException {
        return parseToken(token).get("sub");
    }
    public String getSubject(Map<String, String> claims) {
        return claims.get("sub");
    }

    // get expiration
    public long getExpiration(String token) throws JsonProcessingException {
        return Long.parseLong(parseToken(token).get("exp"));
    }
    public long getExpiration(Map<String, String> claims) {
        return Long.parseLong(claims.get("exp"));
    }

    // is expired
    public boolean isExpired(String token) throws JsonProcessingException {
        Map<String, String> claims = parseToken(token);
        if (getExpiration(claims) < System.currentTimeMillis()) {
            String key = getSubject(claims);
            if (redisUtil.hasKey(key)) {
                redisUtil.expire(key, EXPIRATION_REFRESH);
                return false;
            }
            return true;
        }
        return false;
    }
    public boolean isExpired(String subject, long expiration) {
        if (expiration < System.currentTimeMillis()) {
            if (redisUtil.hasKey(subject)) {
                redisUtil.expire(subject, EXPIRATION_REFRESH);
                return false;
            }
            return true;
        }
        return false;
    }

    // is valid
    public boolean isValid(String token) throws JsonProcessingException {
        Map<String, String> claims = parseToken(token);
        String key = getSubject(claims);
        if (redisUtil.hasKey(key)) {
            if (redisUtil.get(key).equals(getExpiration(claims))) {
                return true;
            }
        }
        return false;
    }
    public boolean isValid(String subject, long expiration) {
        if (redisUtil.hasKey(subject)) {
            if (Long.parseLong((String)redisUtil.get(subject)) == expiration) {
                return true;
            }
        }
        return false;
    }

}
