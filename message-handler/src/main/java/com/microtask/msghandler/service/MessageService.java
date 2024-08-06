package com.microtask.msghandler.service;

import com.microtask.msghandler.entity.MessageEntity;
import com.microtask.msghandler.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository repository;

    @Retryable(retryFor = SQLException.class, maxAttempts = 5)
    public String save(String request) throws ExecutionException, InterruptedException {
        return
            CompletableFuture.supplyAsync(() -> {
                        MessageEntity saveReq = new MessageEntity(request);
                        MessageEntity res = repository.save(saveReq);
                        log.info(String.format("Message saved at %s with id : %d",
                                LocalDateTime.now()
                                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")),
                                res.getId()));

                        return  res.getId().toString();
            }).get();
    }
}
