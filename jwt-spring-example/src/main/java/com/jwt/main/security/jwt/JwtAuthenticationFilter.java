package com.jwt.main.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.main.security.jwt.models.EmailAndPasswordAuthenticationRequest;
import com.jwt.main.security.jwt.models.JwtConfig;
import com.jwt.main.security.jwt.models.JwtTokenResponse;
import com.jwt.main.security.jwt.services.UserDetailsImpl;
import com.jwt.main.security.jwt.utils.JwtTokenCreatorUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


// Checks username and password and issue a token
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager,
                                   final JwtConfig jwtConfig) {
        setFilterProcessesUrl("/api/user/login");
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response) throws AuthenticationException {
        try {
            final EmailAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), EmailAndPasswordAuthenticationRequest.class);
            final Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            );
            return authenticationManager.authenticate(authentication);

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authentication) throws IOException {
        final UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        final JwtTokenCreatorUtil jwtTokenCreatorUtil = new JwtTokenCreatorUtil(user, jwtConfig, request.getRequestURL().toString());
        final JwtTokenResponse jwtTokenResponse = jwtTokenCreatorUtil.generateAccessToken();
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), jwtTokenResponse);
    }
}
