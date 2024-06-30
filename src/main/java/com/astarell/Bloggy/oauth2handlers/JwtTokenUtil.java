package com.astarell.Bloggy.oauth2handlers;

import com.astarell.Bloggy.models.Account;
import com.nimbusds.openid.connect.sdk.claims.ClaimsSet;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class JwtTokenUtil implements Serializable {
    public static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60L;

    // signing key for testing
    public static final byte[] signing_bytes = new byte[36];
//    public static final String SIGNING_KEY = "bloggy123";

    public static String generateToken(Account account){
        Claims claims = Jwts.claims().setSubject(account.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://localhost:9191/")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, signing_bytes)
                .compact();
    }

}
