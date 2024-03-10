package com.chatbot.answer.service;

import com.chatbot.answer.dto.AnswerDto.ResponseDto;
import com.chatbot.answer.entity.Answer;
import com.chatbot.answer.repository.AnswerQuerydslRepository;
import com.chatbot.block.service.QuizBlockService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class AnswerService {
    //    private final AnswerRepository answerRepository;
    private final AnswerQuerydslRepository answerRepository;
    private final QuizBlockService quizBlockService;

    public ResponseDto checkAnswer(Answer userAnswer) {
        String validatedAnswer = validate(userAnswer);
        if (validatedAnswer == null) {
            validatedAnswer = userAnswer.getAnswer();
        }

        Answer answer = validateCorrectAnswer(userAnswer, validatedAnswer);
        if (answer == null) {
            answer = new Answer(0L, userAnswer.getEpisode(), userAnswer.getQuizNumber(), null);
        }

        String blockId = getBlockId(answer);
        String answerBlockId = getAnswerBlockId(answer);

        return Answer.answerToResponseDto(answer, blockId, answerBlockId,
                (userAnswer.getEpisode() == 0 && userAnswer.getQuizNumber() == 0));
    }

    private String getBlockId(Answer answer) {
        if (answer.getAnswer() == null) {
            return quizBlockService.findBlockId(answer.getEpisode(), answer.getQuizNumber(), false, false);
        }
        return quizBlockService.findBlockId(answer.getEpisode(), answer.getQuizNumber(), true, false);
    }

    private String getAnswerBlockId(Answer answer) {
        return quizBlockService.findBlockId(answer.getEpisode(), answer.getQuizNumber(), true, true);
    }

    private Answer validateCorrectAnswer(Answer userAnswer, String answer) {
        return answerRepository.findByEpisodeAndQuizNumberAndAnswer(userAnswer.getEpisode(), userAnswer.getQuizNumber(),
                answer).orElse(null);
    }

    private String validate(Answer answer) {
        if (answer.getEpisode() == 1 && answer.getQuizNumber() == 1) {
            if (answer.getAnswer().equals("qk3517") || answer.getAnswer().equals("qk3.517") || answer.getAnswer()
                    .equals("3.517") || answer.getAnswer().equals("3517")) {
                return "qk-3.517";
            }
        }

        if (answer.getEpisode() == 2 && answer.getQuizNumber() == 2) {
            if (answer.getAnswer().equals("2020") || answer.getAnswer().equals("20시20분") || answer.getAnswer()
                    .equals("20시20")) {
                return "20:20";
            }
        }

        return null;
    }
}
