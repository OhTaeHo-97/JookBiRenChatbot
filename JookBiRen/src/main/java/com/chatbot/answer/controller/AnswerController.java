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

    @PostMapping("/quizInput00")
    public ResponseEntity quiz00(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput11")
    public ResponseEntity quiz11(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput12")
    public ResponseEntity quiz12(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput13")
    public ResponseEntity quiz13(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput14")
    public ResponseEntity quiz14(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput15")
    public ResponseEntity quiz15(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput16")
    public ResponseEntity quiz16(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput17")
    public ResponseEntity quiz17(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput21")
    public ResponseEntity quiz21(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput22")
    public ResponseEntity quiz22(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput23")
    public ResponseEntity quiz23(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput24")
    public ResponseEntity quiz24(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput32")
    public ResponseEntity quiz32(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quizInput33")
    public ResponseEntity quiz33(@RequestBody AnswerPostDto quizInfo) {
        ResponseDto response = answerService.checkAnswer(Answer.answerPostDtoToAnswer(quizInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
