package com.hrizzon.demo2.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class JwUtils {

    public String generateToken(AppUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256, "azerty")
//                .setExpiration(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000))) // Permet de donner une expiration à la connexion
                .compact();

    }

    public String getSubjectFromJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey("azerty")
                .parseClaimsJwt(jwt)
                .getBody()
                .getSubject();
    }
}
