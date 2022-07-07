package com.jwt.main.auth.controllers;

import com.jwt.main.auth.services.RefreshTokenVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    RefreshTokenVerifyService refreshTokenVerifyService;

    @GetMapping("/token/refresh")
    public void refreshToken(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        refreshTokenVerifyService.verifyRefreshToken(request, response);
    }
}
