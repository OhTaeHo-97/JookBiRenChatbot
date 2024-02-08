package com.chatbot.answer.controller;

import com.chatbot.answer.dto.AnswerDto.AnswerPostDto;
import com.chatbot.answer.dto.AnswerDto.ResponseDto;
import com.chatbot.answer.entity.Answer;
import com.chatbot.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/quiz")
    public ResponseEntity quiz1(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
