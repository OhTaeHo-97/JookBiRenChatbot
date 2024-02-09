package com.chatbot.user.controller;

import com.chatbot.user.dto.UserDto.ResponseDto;
import com.chatbot.user.dto.UserDto.UserPostDto;
import com.chatbot.user.entity.User;
import com.chatbot.user.service.UserService;
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
        ResponseDto response = userService.login(User.userPostDtoToUser(userInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/loginc")
    public ResponseEntity customLogin(@RequestBody UserPostDto userInfo) {
        ResponseDto response = userService.login(User.userPostDtoToUser(userInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}