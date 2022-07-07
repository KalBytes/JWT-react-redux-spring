package com.jwt.main.auth.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.jwt.main.security.jwt.models.JwtConfig;
import com.jwt.main.security.jwt.models.JwtTokenResponse;
import com.jwt.main.security.jwt.services.UserDetailsImpl;
import com.jwt.main.security.jwt.utils.JwtTokenCreatorUtil;
import com.jwt.main.security.jwt.utils.JwtTokenVerifierUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class RefreshTokenVerifyService {
    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenVerifyService.class);

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    UserDetailsService userDetailsService;

    public void verifyRefreshToken(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String tokenPrefix = jwtConfig.getTokenPrefix() + " ";
        if (authorizationHeader != null && authorizationHeader.startsWith(tokenPrefix)) {
            try {
                final String token = authorizationHeader.substring(tokenPrefix.length());
                final String refresh_token = authorizationHeader.substring("Bearer ".length());
                final JwtTokenVerifierUtil jwtTokenVerifierUtil = new JwtTokenVerifierUtil(response, token, jwtConfig);
                jwtTokenVerifierUtil.verifyRefreshToken();
                final String email = jwtTokenVerifierUtil.getSubject();
                final UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);
                final JwtTokenCreatorUtil jwtTokenCreatorUtil = new JwtTokenCreatorUtil(user, jwtConfig, request.getRequestURL().toString());
                final JwtTokenResponse jwtTokenResponse = jwtTokenCreatorUtil.generateAccessToken();
                jwtTokenResponse.setRefreshToken(refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), jwtTokenResponse);
            } catch (final Exception e) {
                throw new IOException(e);
            }
        } else {
            logger.error("Refresh token is missing");
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
