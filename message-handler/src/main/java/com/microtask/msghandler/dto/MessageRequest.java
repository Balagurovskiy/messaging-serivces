package com.microtask.msghandler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    //TDDO reconsider to add field or remove dto
    private String msg;
}
