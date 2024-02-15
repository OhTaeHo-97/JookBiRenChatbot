package com.chatbot.custom.controller;

import com.chatbot.custom.dto.CustomDto.CustomImageDto;
import com.chatbot.custom.dto.CustomDto.CustomImageResponseDto;
import com.chatbot.custom.dto.CustomDto.CustomOpenDto;
import com.chatbot.custom.dto.CustomDto.ResponseDto;
import com.chatbot.custom.entity.Custom;
import com.chatbot.custom.service.CustomService;
import com.chatbot.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CustomController {
    private final CustomService customService;

    @PostMapping("/custom")
    public ResponseEntity customImage(@RequestBody CustomImageDto customOpenInfo) {
        CustomImageResponseDto response = customService.makeCustomImage(Custom.customImageDtoToCustoms(customOpenInfo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/custom1")
    public ResponseEntity customOpen1(@RequestBody CustomOpenDto customOpenInfo) {
        ResponseDto response = customService.openCustom(User.customOpenDtoToUser(customOpenInfo), 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/custom2")
    public ResponseEntity customOpen2(@RequestBody CustomOpenDto customOpenInfo) {
        ResponseDto response = customService.openCustom(User.customOpenDtoToUser(customOpenInfo), 1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/custom3")
    public ResponseEntity customOpen3(@RequestBody CustomOpenDto customOpenInfo) {
        ResponseDto response = customService.openCustom(User.customOpenDtoToUser(customOpenInfo), 2);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
