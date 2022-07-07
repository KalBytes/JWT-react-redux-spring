package com.jwt.main.security.jwt.models;


import com.google.common.net.HttpHeaders;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;
    private Integer refreshTokenExpirationAfterDays;

    static JwtConfig jwtConfig() {
        return new JwtConfig();
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(final String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(final String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Integer getTokenExpirationAfterDays() {
        return tokenExpirationAfterDays;
    }

    public void setTokenExpirationAfterDays(final Integer tokenExpirationAfterDays) {
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
    }

    public Integer getRefreshTokenExpirationAfterDays() {
        return refreshTokenExpirationAfterDays;
    }

    public void setRefreshTokenExpirationAfterDays(final Integer refreshTokenExpirationAfterDays) {
        this.refreshTokenExpirationAfterDays = refreshTokenExpirationAfterDays;
    }

    public static String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
