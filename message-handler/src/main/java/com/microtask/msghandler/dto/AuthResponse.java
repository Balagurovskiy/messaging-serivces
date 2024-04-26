package com.microtask.msghandler.dto;


public record AuthResponse(String token, int expTimeSec) {
}
