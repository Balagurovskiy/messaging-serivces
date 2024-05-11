package com.microtask.msggenerator.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtEncoder jwtEncoder;
    private final int EXPIRATION_TIME = 10;
    private final ChronoUnit EXPIRATION_UNITS = ChronoUnit.MINUTES;

    public String create(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(EXPIRATION_TIME, EXPIRATION_UNITS))
                .subject(username)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    public int getExpTime() {
        return EXPIRATION_TIME;
    }
    public long getExpUnit() {
        return EXPIRATION_UNITS.getDuration().getSeconds();
    }

}
