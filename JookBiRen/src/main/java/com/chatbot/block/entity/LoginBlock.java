package com.chatbot.block.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class LoginBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String blockId;
    private boolean isSuccessfulLogin;
    private boolean isTutorial;
    private boolean isStory;

    @Override
    public String toString() {
        return "LoginBlock{" +
                "id=" + id +
                ", blockId='" + blockId + '\'' +
                ", isSuccessfulLogin=" + isSuccessfulLogin +
                ", isTutorial=" + isTutorial +
                ", isStory=" + isStory +
                '}';
    }
}
