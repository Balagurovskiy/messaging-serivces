package com.microtask.msghandler.service;

import com.microtask.msghandler.entity.UserEntity;
import com.microtask.msghandler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StartupUserSetup implements CommandLineRunner {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
   @Override
    public void run(String...args) throws Exception {
       UserEntity defUser = new UserEntity("message-generator", "123");
       defUser.setPassword(passwordEncoder.encode(defUser.getPassword()));
       UserEntity savedUser = userRepository.save(defUser);
       log.info("Default user created : " + savedUser);
    }
}