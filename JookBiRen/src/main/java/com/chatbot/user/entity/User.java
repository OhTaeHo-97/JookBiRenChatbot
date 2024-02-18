package com.chatbot.user.entity;

import com.chatbot.custom.dto.CustomDto.Custom1To2Dto;
import com.chatbot.custom.dto.CustomDto.Custom3To4Dto;
import com.chatbot.custom.dto.CustomDto.CustomOpenDto;
import com.chatbot.user.dto.UserDto.BannedTextCard;
import com.chatbot.user.dto.UserDto.Button;
import com.chatbot.user.dto.UserDto.Output;
import com.chatbot.user.dto.UserDto.ResponseDto;
import com.chatbot.user.dto.UserDto.Template;
import com.chatbot.user.dto.UserDto.TextCard;
import com.chatbot.user.dto.UserDto.UserPostDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    private String firstId;
    private String secondId;
    private boolean isBanned;
    private boolean isDlc;
    private int custom;

    private User(String code, String kakaoId) {
        this.code = code;
        this.firstId = kakaoId;
    }

    public static User of(String code, String kakaoId) {
        return new User(code, kakaoId);
    }

    public static User userPostDtoToUser(UserPostDto userPostDto) {
        return new User(userPostDto.getAction().getParams().getInput_pw(),
                userPostDto.getUserRequest().getUser().getId());
    }

    public static User customOpenDtoToUser(CustomOpenDto customOpenDto) {
        return new User(null, customOpenDto.getUserRequest().getUser().getId());
    }

    public static User custom1To2DtoToUser(Custom1To2Dto custom1To2Dto) {
        return new User(null, custom1To2Dto.getUserRequest().getUser().getId());
    }

    public static User custom3To4DtoToUser(Custom3To4Dto custom3To4Dto) {
        return new User(null, custom3To4Dto.getUserRequest().getUser().getId());
    }

    private static final String SUCCESSFUL_LOGIN = "%s님 환영합니다! ";
    private static final String BANNED_LOGIN = "%s는 중복 로그인으로 접근이 불가능한 계정입니다.\n";
    private static final String OVERLAPPING_LOGIN = "%s는 이미 다른 kakaoID에서 로그인 된 계정입니다.\n";
    private static final String INVALID_CODE = "%s는 없는 접속코드 입니다.\n다시 입력해 주세요.";
    private static final String TUTORIAL = "튜토리얼";
    private static final String STORY = "스토리";
    private static final String RE_INPUT = "다시 입력 하기";

    public static ResponseDto userToResponseDto(User user, int status, String blockId, String tutorialId) {
        if (status == 1) {
            return makeSuccessfulLoginResponse(user, blockId, tutorialId);
        } else if (status == 2) {
            return makeBannedResponse(user);
        } else if (status == 3) {
            return makeOverlappingLoginResponse(user);
        }
        return makeInvalidCodeResponse(user, blockId);
    }

    private static ResponseDto makeSuccessfulLoginResponse(User user, String blockId, String tutorialId) {
        List<Button> buttons = new ArrayList<>();
        buttons.add(makeButton(TUTORIAL, tutorialId));
        buttons.add(makeButton(STORY, blockId));
        TextCard textCard = TextCard.of(String.format(SUCCESSFUL_LOGIN, user.getCode()),
                Collections.unmodifiableList(buttons));
        List<Output> outputs = new ArrayList<>();
        outputs.add(Output.of(textCard));
        Template template = new Template(outputs);
        ResponseDto responseDto = new ResponseDto(template);

        return responseDto;
    }

    private static ResponseDto makeBannedResponse(User user) {
        BannedTextCard textCard = new BannedTextCard(String.format(BANNED_LOGIN, user.getCode()));
        List<Output> outputs = new ArrayList<>();
        outputs.add(Output.of(textCard));
        Template template = new Template(outputs);
        ResponseDto responseDto = new ResponseDto(template);

        return responseDto;
    }

    private static ResponseDto makeOverlappingLoginResponse(User user) {
        BannedTextCard textCard = new BannedTextCard(String.format(OVERLAPPING_LOGIN, user.getCode()));
        List<Output> outputs = new ArrayList<>();
        outputs.add(Output.of(textCard));
        Template template = new Template(outputs);
        ResponseDto responseDto = new ResponseDto(template);

        return responseDto;
    }

    private static ResponseDto makeInvalidCodeResponse(User user, String blockId) {
        List<Button> buttons = new ArrayList<>();
        buttons.add(makeButton(RE_INPUT, blockId));
        TextCard textCard = TextCard.of(String.format(INVALID_CODE, user.getCode()),
                Collections.unmodifiableList(buttons));
        List<Output> outputs = new ArrayList<>();
        outputs.add(Output.of(textCard));
        Template template = new Template(outputs);
        ResponseDto responseDto = new ResponseDto(template);

        return responseDto;
    }

    private static Button makeButton(String label, String blockId) {
        return Button.of(label, blockId);
    }
}
