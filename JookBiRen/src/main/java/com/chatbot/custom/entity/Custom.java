package com.chatbot.custom.entity;

import static com.chatbot.custom.util.CustomConstant.EYES;
import static com.chatbot.custom.util.CustomConstant.NECKLACE;
import static com.chatbot.custom.util.CustomConstant.POSE;
import static com.chatbot.custom.util.CustomConstant.SNOUT;
import static com.chatbot.custom.util.CustomConstant.SUNGLASSES;

import com.chatbot.custom.dto.CustomDto.CustomImageDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Custom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String category;
    private String type;
    private int value;

    private Custom(String category, String type) {
        this.category = category;
        this.type = type;
    }

    public static List<Custom> customImageDtoToCustoms(CustomImageDto customImageInfo) {
        List<Custom> customs = new ArrayList<>();
        customs.add(new Custom(EYES, customImageInfo.getAction().getParams().getInput_eyes()));
        customs.add(new Custom(SUNGLASSES, customImageInfo.getAction().getParams().getInput_sunglasses()));
        customs.add(new Custom(SNOUT, customImageInfo.getAction().getParams().getInput_snout()));
        customs.add(new Custom(NECKLACE, customImageInfo.getAction().getParams().getInput_necklace()));
        customs.add(new Custom(POSE, customImageInfo.getAction().getParams().getPose()));
        return customs;
    }
}
