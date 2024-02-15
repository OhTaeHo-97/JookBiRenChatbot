package com.chatbot.custom.dto;

import java.util.List;
import lombok.Getter;

public class CustomDto {
    private static final String ACTION = "block";
    private static final String VERSION = "2.0";
    private static final String NOT_EXIST_CODE = "는 없는 접속코드 입니다.\n다시 입력해 주세요.";
    private static final String ALT_TEXT = "커스텀입니다";

    @Getter
    public static class CustomOpenDto {
        private User userRequest;
    }

    @Getter
    public static class CustomImageDto {
        private Action action;
    }

    @Getter
    public static class Action {
        private Params params;
    }

    @Getter
    public static class Params {
        private String input_eyes;
        private String input_sunglasses;
        private String input_snout;
        private String input_necklace;
        private String pose;
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

        public ResponseDto(Template template) {
            this.template = template;
        }
    }

    @Getter
    public static class Template {
        private List<Output> outputs;

        public Template(List<Output> outputs) {
            this.outputs = outputs;
        }
    }

    @Getter
    public static class Output {
        private TextCard textCard;

        public Output(TextCard textCard) {
            this.textCard = textCard;
        }
    }

    @Getter
    public static class TextCard {
        private String text = NOT_EXIST_CODE;
    }

    @Getter
    public static class CustomImageResponseDto {
        private String version = VERSION;
        private ImageTemplate template;

        public CustomImageResponseDto(ImageTemplate template) {
            this.template = template;
        }
    }

    @Getter
    public static class ImageTemplate {
        private List<ImageOutput> outputs;
        private List<QuickReply> quickReplies;

        public ImageTemplate(List<ImageOutput> outputs, List<QuickReply> quickReplies) {
            this.outputs = outputs;
            this.quickReplies = quickReplies;
        }
    }

    @Getter
    public static class ImageOutput {
        private SimpleImage simpleImage;

        public ImageOutput(SimpleImage simpleImage) {
            this.simpleImage = simpleImage;
        }
    }

    @Getter
    public static class SimpleImage {
        private String imageUrl;
        private String altText = ALT_TEXT;

        public SimpleImage(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    @Getter
    public static class QuickReply {
        private String messageText;
        private String action = ACTION;
        private String blockId;
        private String label;

        public QuickReply(String messageText, String blockId, String label) {
            this.messageText = messageText;
            this.blockId = blockId;
            this.label = label;
        }
    }
}
