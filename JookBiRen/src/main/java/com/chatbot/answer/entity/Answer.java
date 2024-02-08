package com.chatbot.answer.entity;

import com.chatbot.answer.dto.AnswerDto.AnswerPostDto;
import com.chatbot.answer.dto.AnswerDto.Button;
import com.chatbot.answer.dto.AnswerDto.Output;
import com.chatbot.answer.dto.AnswerDto.ResponseDto;
import com.chatbot.answer.dto.AnswerDto.Template;
import com.chatbot.answer.dto.AnswerDto.TextCard;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int episode;
    private int quizNumber;
    private String answer;

    public static Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto) {
        String userInput = answerPostDto.getAction().getParams().getSys_text();
        String blockName = answerPostDto.getUserRequest().getBlock().getName();

        int episode = blockName.charAt(2) - '0';
        int quizNumber = Integer.parseInt(blockName.substring(4));

        return new Answer(0, episode, quizNumber, userInput.replaceAll(" ", "").toLowerCase());
    }

    private static final String CORRECT_TEXT = "문제 %d-%d 정답입니다.";
    private static final String ANSWER_TEXT = "문제 %d-%d 해설";
    private static final String NEXT_TEXT = "다음";
    private static final String WRONG_TEXT = "문제 %d-%d 오답입니다.";
    private static final String REPLAY_TEXT = "문제 다시 보기";

    public static ResponseDto answerToResponseDto(Answer answer, String blockId, String answerId) {
        return makeResponseDto(answer, blockId, answerId);
    }

    private static ResponseDto makeResponseDto(Answer answer, String blockId, String answerId) {
        List<Button> buttons = makeButtons(answer, blockId, answerId);
        TextCard textCard = makeTextCards(answer, buttons);
        List<Output> outputs = new ArrayList<>();
        Output output = Output.of(textCard);
        outputs.add(output);
        Template template = new Template(outputs);
        ResponseDto responseDto = new ResponseDto(template);

        return responseDto;
    }

    private static TextCard makeTextCards(Answer answer, List<Button> buttons) {
        List<TextCard> textCards = new ArrayList<>();
        if (answer.getAnswer() == null) {
            return TextCard.of(String.format(WRONG_TEXT, answer.getEpisode(), answer.getQuizNumber()),
                    Collections.unmodifiableList(buttons));
        }
        return TextCard.of(String.format(CORRECT_TEXT, answer.getEpisode(), answer.getQuizNumber()),
                Collections.unmodifiableList(buttons));
    }

    private static List<Button> makeButtons(Answer answer, String blockId, String answerId) {
        if (answer.getAnswer() == null) {
            return makeWrongButtons(blockId);
        }
        return makeCorrectButtons(answer, blockId, answerId);
    }

    private static List<Button> makeCorrectButtons(Answer answer, String blockId, String answerId) {
        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.of(String.format(ANSWER_TEXT, answer.getEpisode(), answer.getQuizNumber()), answerId));
        buttons.add(Button.of(NEXT_TEXT, blockId));
        return buttons;
    }

    private static List<Button> makeWrongButtons(String blockId) {
        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.of(REPLAY_TEXT, blockId));
        return buttons;
    }
}
