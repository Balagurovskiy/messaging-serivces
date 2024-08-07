package com.microtask.msggenerator.service;

import com.microtask.msggenerator.dto.MessageRequest;
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

    @Value("${routing.message.url}")
    private String url;

    @Value("${routing.message.host}")
    private String host;

    public String send(String token, MessageRequest body){
        String res = "";
        String theUrl = String.format("http://%s/%s" , host, url);
        RestTemplate restTemplate = new RestTemplate();
        log.info(String.format("Request :: %s :: %s", theUrl, body));
        try {
            HttpEntity<MessageRequest> entity = new HttpEntity<MessageRequest>(
                    body,
                    httpHeaderBuilder.createBearerHeaders(token));
            ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.POST, entity, String.class);
            log.info("Response :: status ("+ response.getStatusCode() + ") :: body: " + response.getBody());
            res = response.getBody();
        }
        catch (Exception eek) {
            log.error("Exception :: "+ eek.getMessage());
        }
        return res;
    }




}
