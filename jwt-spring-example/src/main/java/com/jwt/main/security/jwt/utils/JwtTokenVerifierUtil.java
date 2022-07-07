package com.jwt.main.security.jwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.main.security.jwt.models.JwtConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class JwtTokenVerifierUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenVerifierUtil.class);

    private final HttpServletResponse response;
    private final String token;
    private final JwtConfig jwtConfig;
    private String tokenSubject;

    public JwtTokenVerifierUtil(final HttpServletResponse response, final String token, final JwtConfig jwtConfig) {
        this.response = response;
        this.token = token;
        this.jwtConfig = jwtConfig;
    }

    public String getSubject() {
        return tokenSubject;
    }

    private void setTokenSubject(final String subject) {
        tokenSubject = subject;
    }

    public void verifyToken() throws IOException {
        try {
            final DecodedJWT decodedJWT = decodedJWT();
            setTokenSubject(decodedJWT.getSubject());
            final String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
            final UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(tokenSubject, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (final Exception exception) {
            logger.error("Access token verify error: {}", exception.getMessage());
            exception(exception.getMessage());
        }
    }

    public void verifyRefreshToken() throws IOException {
        try {
            final DecodedJWT decodedJWT = decodedJWT();
            setTokenSubject(decodedJWT.getSubject());
        } catch (final JWTVerificationException exception) {
            logger.error("Refresh token verify error: {}", exception.getMessage());
            exception(exception.getMessage());
        }
    }

    private DecodedJWT decodedJWT() throws JWTVerificationException {
        final Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes());
        final JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    private void exception(final String message) throws IOException {
        response.setHeader("error", message);
        response.setStatus(FORBIDDEN.value());
        final Map<String, String> error = new HashMap<>();
        error.put("error_message", message);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
