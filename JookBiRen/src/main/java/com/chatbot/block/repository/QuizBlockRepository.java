package com.chatbot.block.repository;

import com.chatbot.block.entity.QuizBlock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizBlockRepository extends JpaRepository<QuizBlock, Long> {
    Optional<QuizBlock> findByEpisodeAndQuizNumberAndIsCorrectAndIsAnswer(int episode, int quizNumber,
                                                                          boolean isCorrect,
                                                                          boolean isAnswer);
}
