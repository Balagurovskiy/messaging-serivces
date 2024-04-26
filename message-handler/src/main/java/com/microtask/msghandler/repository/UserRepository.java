package com.microtask.msghandler.repository;


import com.microtask.msghandler.entity.MessageEntity;
import com.microtask.msghandler.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}
