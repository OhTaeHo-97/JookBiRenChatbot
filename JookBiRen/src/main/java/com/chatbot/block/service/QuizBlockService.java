package com.chatbot.block.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizBlockService {
    //    private final QuizBlockRepository quizBlockRepository;
//    private final QuizBlockQuerydslRepository quizBlockRepository;

//    public String findBlockId(int episode, int quizNumber, boolean isCorrect, boolean isAnswer) {
//        QuizBlock block = validateBlock(episode, quizNumber, isCorrect, isAnswer);
//        if (block == null) {
//            return new String();
//        }
//        return block.getBlockId();
//    }
//
//    private QuizBlock validateBlock(int episode, int quizNumber, boolean isCorrect, boolean isAnswer) {
//        return quizBlockRepository.findByEpisodeAndQuizNumberAndIsCorrectAndIsAnswer(episode, quizNumber, isCorrect,
//                isAnswer).orElse(null);
//    }
}
