package com.microtask.msggenerator.service;

import com.microtask.msggenerator.dto.MessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;

@Configuration
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class MessageSchedule {
    private final MessageSendingService sendingService;
    private final AuthService authService;

    //TODO refactor into user input message (1 .. n)
    @Async
    @Scheduled(fixedRate = 500, initialDelay = 5000)
    public void scheduleFixedRateTaskAsync(){
        if(Objects.nonNull(authService.getToken())){
            String msg = "### Message text ### ";
            log.info("Sending message :" + msg);
            sendingService.send(
                    authService.getToken(),
                    new MessageRequest(msg)
            );
        } else {
            log.info("No token for message");
        }
    }
}
