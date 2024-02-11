package com.chatbot.custom.dto;

import java.util.List;
import lombok.Getter;

public class CustomDto {
    private static final String VERSION = "2.0";
    private static final String NOT_EXIST_CODE = "는 없는 접속코드 입니다.\n다시 입력해 주세요.";

    @Getter
    public static class CustomOpenDto {
        private User userRequest;
    }

    @Getter
    public static class User {
        private KakaoId user;
    }

    @Getter
    public static class KakaoId {
        private String id;
    }

    @Getter
    public static class ResponseDto {
        private String version = VERSION;
        private Template template;
    }

    public static class Template {
        private List<Output> outputs;
    }

    public static class Output {
        private TextCard textCard;
    }

    public static class TextCard {
        private String text = NOT_EXIST_CODE;
    }
}
