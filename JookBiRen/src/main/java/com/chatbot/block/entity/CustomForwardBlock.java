package com.chatbot.block.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CustomForwardBlock {
    private String category;
    private boolean isPossible;
    private String blockId;
}
