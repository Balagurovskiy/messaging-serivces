package com.microtask.msggenerator.service;

import com.microtask.msggenerator.security.JwtTokenService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class AuthService {

    private final JwtTokenService jwtTokenService;
    private String token;

    public void refreshToken(){
        this.token = jwtTokenService.create("message-generator");
    }

    public String getToken() {
        return token;
    }

    public int getExpiresSec() {
        return (int) (jwtTokenService.getExpTime() * jwtTokenService.getExpUnit());
    }
}
