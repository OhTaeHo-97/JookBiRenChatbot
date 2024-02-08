package com.chatbot.user.controller;

import com.chatbot.answer.dto.AnswerDto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserPostDto userInfo) {
        ResponseDto response = userInfo.Login(User.userPostDtoToUser(userInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}