package com.chatbot.custom.service;

import com.chatbot.block.service.CustomBlockService;
import com.chatbot.custom.dto.CustomDto.CustomImageResponseDto;
import com.chatbot.custom.dto.CustomDto.ImageOutput;
import com.chatbot.custom.dto.CustomDto.ImageTemplate;
import com.chatbot.custom.dto.CustomDto.Output;
import com.chatbot.custom.dto.CustomDto.QuickReply;
import com.chatbot.custom.dto.CustomDto.ResponseDto;
import com.chatbot.custom.dto.CustomDto.SimpleImage;
import com.chatbot.custom.dto.CustomDto.Template;
import com.chatbot.custom.dto.CustomDto.TextCard;
import com.chatbot.custom.entity.Custom;
import com.chatbot.custom.repository.CustomRepository;
import com.chatbot.user.entity.User;
import com.chatbot.user.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomService {
    private final UserService userService;
    private final CustomRepository customRepository;
    private final CustomBlockService customBlockService;

    @Value("${custom.image.url}")
    private String IMAGE_URL;

    public CustomImageResponseDto makeCustomImage(List<Custom> customInputs) {
        String imageName = makeCustomImageName(customInputs);
        String imageUrl = String.format(IMAGE_URL, imageName);
        String remakeBlockId = customBlockService.findCustomBlockId(false);
        String initialBlockId = customBlockService.findCustomBlockId(true);
        return customToCustomImageResponseDto(imageUrl, remakeBlockId, initialBlockId);
    }

    private String makeCustomImageName(List<Custom> customInputs) {
        String imageName = "";
        for (Custom custom : customInputs) {
            imageName += findCustomImageName(custom);
        }
        return imageName;
    }

    private String findCustomImageName(Custom custom) {
        Custom result = findCustomByCategoryAndType(custom.getCategory(), custom.getType());
        if (result == null) {
            return "error";
        }
        return String.valueOf(result.getValue());
    }

    private Custom findCustomByCategoryAndType(String category, String type) {
        return customRepository.findByCategoryAndAndType(category, type).orElse(null);
    }

    // 응답 DTO 생성
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

    private static final String REMAKE = "다시 만들기";
    private static final String TO_FIRST = "처음으로";

    private ResponseDto makeResponseDto() {
        TextCard textCard = new TextCard();
        List<Output> outputs = new ArrayList<>();
        Output output = new Output(textCard);
        outputs.add(output);
        Template template = new Template(Collections.unmodifiableList(outputs));
        return new ResponseDto(template);
    }

    public CustomImageResponseDto customToCustomImageResponseDto(String imageUrl, String remakeBlockId,
                                                                 String initialBlockId) {
        return new CustomImageResponseDto(makeImageTemplate(imageUrl, remakeBlockId, initialBlockId));
    }

    private ImageTemplate makeImageTemplate(String imageUrl, String remakeBlockId, String initialBlockId) {
        return new ImageTemplate(makeImageOutputs(imageUrl), makeQuickReplies(remakeBlockId, initialBlockId));
    }

    private List<ImageOutput> makeImageOutputs(String imageUrl) {
        SimpleImage simpleImage = new SimpleImage(imageUrl);
        List<ImageOutput> imageOutputs = new ArrayList<>();
        ImageOutput imageOutput = new ImageOutput(simpleImage);
        imageOutputs.add(imageOutput);
        return imageOutputs;
    }

    private List<QuickReply> makeQuickReplies(String remakeBlockId, String initialBlockId) {
        List<QuickReply> quickReplies = new ArrayList<>();
        quickReplies.add(new QuickReply(REMAKE, remakeBlockId, REMAKE));
        quickReplies.add(new QuickReply(TO_FIRST, initialBlockId, TO_FIRST));
        return quickReplies;
    }
}
