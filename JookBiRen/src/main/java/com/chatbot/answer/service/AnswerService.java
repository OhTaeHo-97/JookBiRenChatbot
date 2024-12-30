package com.chatbot.answer.service;

import static com.chatbot.answer.utils.AnswerConstant.ANSWERS;
import static com.chatbot.block.utils.QuizBlock.QUIZ_BLOCKS;

import com.chatbot.answer.dto.AnswerDto.ResponseDto;
import com.chatbot.answer.entity.Answer;
import com.chatbot.block.service.QuizBlockService;
import com.chatbot.dto.QuizBlockDto;
import com.chatbot.quiz.entity.Quiz;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class AnswerService {
    private static final Set<String> POSSIBLE_ANSWERS_11 = new HashSet<>() {{
        add("qk3517");
        add("qk3.517");
        add("3.517");
        add("3517");
    }};
    private static final Set<String> POSSIBLE_ANSWERS_22 = new HashSet<>() {{
        add("20시20분");
        add("20시 20분");
        add("20: 20");
        add("20 :20");
        add("20 : 20");
        add("2020");
        add("20 20");
        add("오후8시20분");
        add("오후 8시20분");
        add("오후 8시 20분");
        add("오후8시 20분");
    }};

    //    private final AnswerRepository answerRepository;
    private final QuizBlockService quizBlockService;

    public ResponseDto checkAnswer(Answer userAnswer) {
        String validatedAnswer = validate(userAnswer);
//        if (validatedAnswer == null) {
//            validatedAnswer = userAnswer.getAnswer();
//        }

//        Answer answer = validateCorrectAnswer(userAnswer, validatedAnswer);
        boolean isCorrect = validateCorrectAnswer(userAnswer, validatedAnswer);
        Answer answer = null;
        if (!isCorrect) {
            answer = new Answer(userAnswer.getEpisode(), userAnswer.getQuizNumber(), null);
        } else {
            answer = new Answer(userAnswer.getEpisode(), userAnswer.getQuizNumber(), validatedAnswer);
        }
//        if (answer == null) {
//            answer = new Answer(0L, userAnswer.getEpisode(), userAnswer.getQuizNumber(), null);
//        }

        String blockId = getBlockId(answer);
        String answerBlockId = getAnswerBlockId(answer);

        return Answer.answerToResponseDto(answer, blockId, answerBlockId,
                (userAnswer.getEpisode() == 0 && userAnswer.getQuizNumber() == 0));
    }

//    private String getBlockId(Answer answer) {
//        if (answer.getAnswer() == null) {
//            return quizBlockService.findBlockId(answer.getEpisode(), answer.getQuizNumber(), false, false);
//        }
//        return quizBlockService.findBlockId(answer.getEpisode(), answer.getQuizNumber(), true, false);
//    }

    private String getBlockId(Answer answer) {
        if (answer.getAnswer() == null) {
            return QUIZ_BLOCKS.get(new QuizBlockDto(answer.getEpisode(), answer.getQuizNumber(), false, false));
        }
        return QUIZ_BLOCKS.get(new QuizBlockDto(answer.getEpisode(), answer.getQuizNumber(), true, false));
    }

//    private String getAnswerBlockId(Answer answer) {
//        return quizBlockService.findBlockId(answer.getEpisode(), answer.getQuizNumber(), true, true);
//    }

    private String getAnswerBlockId(Answer answer) {
        return QUIZ_BLOCKS.get(new QuizBlockDto(answer.getEpisode(), answer.getQuizNumber(), true, true));
    }

//    private Answer validateCorrectAnswer(Answer userAnswer, String answer) {
//        return answerRepository.findByEpisodeAndQuizNumberAndAnswer(userAnswer.getEpisode(), userAnswer.getQuizNumber(),
//                answer).orElse(null);
//    }

    private boolean validateCorrectAnswer(Answer userAnswer, String answer) {
        Quiz quiz = new Quiz(userAnswer.getEpisode(), userAnswer.getQuizNumber());
        String correctAnswer = ANSWERS.get(quiz);
        return answer.equals(correctAnswer);
    }

    private String validate(Answer answer) {
        if (answer.getEpisode() == 1 && answer.getQuizNumber() == 1) {
            if (POSSIBLE_ANSWERS_11.contains(answer.getAnswer())) {
                return "qk-3.517";
            }
//            if (answer.getAnswer().equals("qk3517") || answer.getAnswer().equals("qk3.517") || answer.getAnswer()
//                    .equals("3.517") || answer.getAnswer().equals("3517")) {
//                return "qk-3.517";
//            }
        }

        if (answer.getEpisode() == 2 && answer.getQuizNumber() == 2) {
            if (POSSIBLE_ANSWERS_22.contains(answer.getAnswer())) {
                return "20:20";
            }
//            if (answer.getAnswer().equals("2020") || answer.getAnswer().equals("20시20분") || answer.getAnswer()
//                    .equals("20시20")) {
//                return "20:20";
//            }
        }

        return answer.getAnswer();
    }
}