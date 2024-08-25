package com.microtask.msghandler.controller;

import com.microtask.msghandler.entity.MessageEntity;
import com.microtask.msghandler.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Slf4j
public class MessagesController {
    private final MessageService service;

    @PostMapping(value = "/", consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String saveMsg(@RequestBody String request) throws ExecutionException, InterruptedException {
        return service.save(request);
    }

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageEntity> getMsg() {
        return service.getAll();
    }

    @GetMapping(value = "/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageEntity getMsg(@PathVariable Long id) {
        return service.getOne(id);
    }
}
