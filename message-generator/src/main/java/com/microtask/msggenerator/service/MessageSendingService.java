package com.microtask.msggenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSendingService{

    private final HttpHeaderBuilder httpHeaderBuilder;
    private final RestTemplate restTemplate;

    @Value("${routing.message.url}")
    private String url;

    @Value("${routing.message.host}")
    private String host;

    @Value("${routing.message.protocol}")
    private String protocol;

    public String send(String token, String body){
        String res = "";
        String theUrl = String.format("%s://%s/%s" , protocol, host, url);
        log.info(String.format("Request :: %s :: %s", theUrl, body));
        try {
            HttpEntity<String> entity = new HttpEntity<String>(
                    body,
                    httpHeaderBuilder.createBearerHeaders(token));
            ResponseEntity<String> response = restTemplate.exchange(
                    theUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            log.info("Response :: status ({}) :: body: {}", response.getStatusCode(), response.getBody());
            res = response.getBody();
        }
        catch (Exception eek) {
            log.error("Exception :: {}", eek.getMessage());
        }
        return res;
    }




}
