package com.chatbot.custom.service;

import com.chatbot.custom.dto.CustomDto.Output;
import com.chatbot.custom.dto.CustomDto.ResponseDto;
import com.chatbot.custom.dto.CustomDto.Template;
import com.chatbot.custom.dto.CustomDto.TextCard;
import com.chatbot.user.entity.User;
import com.chatbot.user.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomService {
    private final UserService userService;

    public ResponseDto openCustom(User userInfo, int customLoc) {
        User user = userService.findUserById(userInfo.getFirstId());
        if (user != null) {
            int customCode = user.getCustom();
            if ((customCode & (1 << (2 - customLoc))) == 0) {
                customCode ^= (1 << (2 - customLoc));
                user.setCustom(customCode);
                userService.updateUser(user);
            }
        }

        return makeResponseDto();
    }

    private ResponseDto makeResponseDto() {
        TextCard textCard = new TextCard();
        List<Output> outputs = new ArrayList<>();
        Output output = new Output(textCard);
        outputs.add(output);
        Template template = new Template(Collections.unmodifiableList(outputs));
        return new ResponseDto(template);
    }
}
