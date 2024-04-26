package com.microtask.msghandler.controller;

import com.microtask.msghandler.dto.AuthResponse;
import com.microtask.msghandler.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenService jwtTokenService;
    @GetMapping("/token")
    public AuthResponse getToken(Authentication authentication) {
        return new AuthResponse(
                jwtTokenService.create(authentication.getName()),
                (int) (jwtTokenService.getExpTime() * jwtTokenService.getExpUnit())

        );
    }
}
