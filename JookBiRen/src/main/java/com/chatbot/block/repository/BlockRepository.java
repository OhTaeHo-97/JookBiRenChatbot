package com.chatbot.block.repository;

import com.chatbot.block.entity.Block;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findByEpisodeAndQuizNumberAndIsCorrectAndIsAnswer(int episode, int quizNumber, boolean isCorrect,
                                                                      boolean isAnswer);
}
