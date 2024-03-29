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
    public String generateToken(String subject) {
        String expiration = Long.toString(System.currentTimeMillis() + EXPIRATION_ACCESS * 1000);
        Map<String, String> claims = new HashMap<>(){{
            put("iss", ISSUER);
            put("sub", subject);
            put("exp", expiration);
        }};
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(claims);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String token = JwtHelper.encode(json, new MacSigner(SECRET)).getEncoded();
        removeToken(subject);
        redisUtil.set(subject, expiration, EXPIRATION_REFRESH);
        return token;
    }

    // remove token
    public void removeToken(String subject) {
        if (redisUtil.hasKey(subject)) {
            redisUtil.delete(subject);
        }
    }

    // parse token
    public Map<String, String> parseToken(String token) {
        String json = JwtHelper.decodeAndVerify(token, new MacSigner(SECRET)).getClaims();
        Map<String, String> claims = null;
        try {
            claims = new ObjectMapper().readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return claims;
    }

    // get subject
    public String getSubject(String token) {
        return parseToken(token).get("sub");
    }
    public String getSubject(Map<String, String> claims) {
        return claims.get("sub");
    }

    // get expiration
    public long getExpiration(String token) {
        return Long.parseLong(parseToken(token).get("exp"));
    }
    public long getExpiration(Map<String, String> claims) {
        return Long.parseLong(claims.get("exp"));
    }

    // is expired
    public boolean isExpired(String token) {
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
    public boolean isValid(String token) {
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
