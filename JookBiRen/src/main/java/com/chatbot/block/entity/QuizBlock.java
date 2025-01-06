package com.chatbot.block.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class QuizBlock {
    private int episode;
    private int quizNumber;
    private boolean isCorrect;
    private boolean isAnswer;
    private String blockId;
}
