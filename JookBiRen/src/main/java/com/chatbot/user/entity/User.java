package com.chatbot.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    private String firstId;
    private String secondId;
    private boolean isBanned;
    private boolean isDlc;
    private int custom;

    private User(String code, String kakaoId) {
        this.code = code;
        this.firstId = kakaoId;
    }

    public static User of(String code, String kakaoId) {
        return new User(code, kakaoId);
    }
}
