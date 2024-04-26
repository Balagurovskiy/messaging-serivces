package com.microtask.msghandler.service;

import com.microtask.msghandler.dto.UserDetailsImpl;
import com.microtask.msghandler.entity.UserEntity;
import com.microtask.msghandler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        return new UserDetailsImpl(user.getLogin(), user.getPassword());
    }
}
