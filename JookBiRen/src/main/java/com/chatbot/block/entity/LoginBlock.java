package com.chatbot.block.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginBlock {
    private String blockId;
    private boolean isSuccessfulLogin;
    private boolean isTutorial;
    private boolean isStory;
}
