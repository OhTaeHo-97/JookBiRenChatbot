package com.chatbot.answer.repository;

import com.chatbot.answer.entity.Answer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByEpisodeAndQuizNumberAndAnswer(int episode, int quizNumber, String answer);
}
