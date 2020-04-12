package com.akijoey.library.response;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JsonWebToken {

    private static final String SECRET = "library";
    private static final String ISS = "echisan";

    private static final long EXPIRATION = 3600L;
    private static final long EXPIRATION_REMEMBER = 604800L;

    public static String generateToken(String username, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    public static String getUsername(String token){
        return parseToken(token).getSubject();
    }

    public static boolean isExpiration(String token){
        return parseToken(token).getExpiration().before(new Date());
    }

    private static Claims parseToken(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

}
