package com.jwt.main.security.jwt;

import com.jwt.main.security.jwt.models.JwtConfig;
import com.jwt.main.security.jwt.utils.JwtTokenVerifierUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    public JwtTokenVerifier(final JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/user/login") || request.getServletPath().equals("/api/token/refresh")) {
            filterChain.doFilter(request, response);
        } else {
            final String authorizationHeader = request.getHeader(AUTHORIZATION);
            final String tokenPrefix = jwtConfig.getTokenPrefix() + " ";
            if (authorizationHeader != null && authorizationHeader.startsWith(tokenPrefix)) {
                final String token = authorizationHeader.substring(tokenPrefix.length());
                final JwtTokenVerifierUtil jwtTokenVerifierUtil = new JwtTokenVerifierUtil(response, token, jwtConfig);
                jwtTokenVerifierUtil.verifyToken();
                filterChain.doFilter(request, response);
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
