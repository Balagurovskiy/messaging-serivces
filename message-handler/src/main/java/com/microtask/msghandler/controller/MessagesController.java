package com.microtask.msghandler.controller;

import com.microtask.msghandler.dto.MessageRequest;
import com.microtask.msghandler.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Slf4j
public class MessagesController {
    private final MessageService service;
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String saveMsg(@RequestBody MessageRequest request) throws ExecutionException, InterruptedException {
        return service.save(request.getMsg());
    }
}
