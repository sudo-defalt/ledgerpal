package com.snappay.ledgerpal.security.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtCredentialService {
    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 30; // 30 minutes
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 days
    private final UserDetailsService userDetailsService;
    private final SecretKey key;
    private final JwtParser jwtParser;

    public JwtCredentialService(@Value("${jwt.secret}") String secret, UserDetailsService userDetailsService) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.userDetailsService = userDetailsService;
        this.jwtParser = Jwts.parser().verifyWith(key).build();
    }

    public JwtCredential generate(UserDetails userDetails) {
        return new JwtCredential(generateAccessToken(userDetails), generateRefreshToken(userDetails));
    }

    private String generateAccessToken(UserDetails userDetails) {
        Date issueTimestamp = new Date();
        Date expirationTimestamp = Date.from(issueTimestamp.toInstant().plusMillis(ACCESS_TOKEN_EXPIRATION));
        return Jwts.builder().subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .issuedAt(issueTimestamp)
                .expiration(expirationTimestamp)
                .signWith(key)
                .compact();
    }

    private String generateRefreshToken(UserDetails userDetails) {
        Date issueTimestamp = new Date();
        Date expirationTimestamp = Date.from(issueTimestamp.toInstant().plusMillis(REFRESH_TOKEN_EXPIRATION));
        return Jwts.builder().subject(userDetails.getUsername())
                .issuedAt(issueTimestamp)
                .expiration(expirationTimestamp)
                .signWith(key)
                .compact();
    }

    public UserDetails extractUser(String token) {
        String username = jwtParser.parseSignedClaims(token).getPayload().getSubject();
        return userDetailsService.loadUserByUsername(username);
    }

    public boolean validate(String token) {
        try {
            jwtParser.parseSignedClaims(token).getPayload().getSubject();
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }
}
