package com.snappay.ledgerpal.security.jwt;

public record JwtCredential(String accessToken, String refreshToken) {
}
