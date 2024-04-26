package com.microtask.msggenerator.service;

import com.microtask.msggenerator.dto.AuthResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class AuthService {

    private final HttpHeaderBuilder httpHeaderBuilder;

    private String token;
    private int expiresSec;

    public String token()
    {
        String theUrl = "http://localhost:8081/auth/token";
        RestTemplate restTemplate = new RestTemplate();
        log.info(String.format("Request :: %s ", theUrl));
        try {
            HttpEntity<String> entity = new HttpEntity<String>(
                    "",
                    httpHeaderBuilder.createOathHeaders("message-generator", "123"));
            ResponseEntity<AuthResponse> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, AuthResponse.class);
            log.info("Response :: status ("+ response.getStatusCode() + ") :: body: " + response.getBody());
            token = response.getBody().token();
            expiresSec = response.getBody().expTimeSec();
        }
        catch (Exception eek) {
            log.error("Exception ::"+ eek.getMessage());
        }
        return token;
    }
}
