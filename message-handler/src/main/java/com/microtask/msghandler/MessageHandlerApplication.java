package com.microtask.msghandler;

import com.microtask.msghandler.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class MessageHandlerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageHandlerApplication.class, args);
    }
}
