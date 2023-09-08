package org.commerce.user.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
@Table(name = "user_")
@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime createdAt;

    public User(String email, String password, String nickname, LocalDateTime createdAt){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }

}
