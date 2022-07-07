package com.jwt.main.security.jwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jwt.main.security.jwt.models.JwtConfig;
import com.jwt.main.security.jwt.models.JwtRefreshTokenResponse;
import com.jwt.main.security.jwt.models.JwtTokenResponse;
import com.jwt.main.security.jwt.services.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenCreatorUtil {
    private final UserDetailsImpl user;
    private final JwtConfig jwtConfig;
    private final String tokenIssuer;
    private final List<String> roles;

    public JwtTokenCreatorUtil(final UserDetailsImpl user, final JwtConfig jwtConfig, final String tokenIssuer) {
        this.user = user;
        this.jwtConfig = jwtConfig;
        this.tokenIssuer = tokenIssuer;
        this.roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public JwtTokenResponse generateAccessToken() {
        final String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date((new Date()).getTime() + 4000))
                .withIssuer(tokenIssuer)
                .withClaim("roles", roles)
                .sign(getAlgorithm());
        return new JwtTokenResponse(
                access_token,
                createRefreshToken(),
                user.getId(),
                user.getEmail(),
                roles
        );
    }

    public JwtRefreshTokenResponse generateAccessToken(final String accessToken) {
        return new JwtRefreshTokenResponse(accessToken, createRefreshToken());
    }

    private String createRefreshToken() {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getRefreshTokenExpirationAfterDays())))
                .withIssuer(tokenIssuer)
                .sign(getAlgorithm());
    }

    Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtConfig.getSecretKey());
    }
}
