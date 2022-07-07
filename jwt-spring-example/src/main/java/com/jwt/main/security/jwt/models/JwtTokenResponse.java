package com.jwt.main.security.jwt.models;

import java.util.Collection;
import java.util.List;

public class JwtTokenResponse {
    private String type = "Bearer";
    private String refreshToken;
    private final String token;
    private final Long id;
    private final String email;
    private final List<String> roles;

    public JwtTokenResponse(
            final String token,
            final String refreshToken,
            final Long id, final String email,
            final List<String> roles) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Collection<String> getRoles() {
        return roles;
    }

}
