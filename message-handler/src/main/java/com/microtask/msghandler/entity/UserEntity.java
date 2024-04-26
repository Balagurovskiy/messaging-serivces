package com.microtask.msghandler.entity;

import com.microtask.msghandler.config.StringEncryptionConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Entity
@Table(name = "user_data")
@NoArgsConstructor
public class UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String login;

    @Column
//    @Convert(converter = StringEncryptionConverter.class)
    private String password;

    public UserEntity(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
