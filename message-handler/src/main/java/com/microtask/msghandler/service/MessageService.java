package com.microtask.msghandler.service;

import com.microtask.msghandler.entity.MessageEntity;
import com.microtask.msghandler.repository.MessageRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository repository;
//    @Observed(name = "posts.save-message", contextualName = "posts.save-message")
    public String save(String request){
        MessageEntity saveReq = new MessageEntity(request);
        MessageEntity res = repository.save(saveReq);
        String resp =  String.format("Message saved at %s with id : %d",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-mm-yyyy hh:MM:ss")),
                res.getId());
        log.info(resp);
        return resp;
    }
}
