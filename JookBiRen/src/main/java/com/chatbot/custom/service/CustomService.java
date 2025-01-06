package com.chatbot.custom.service;

import static com.chatbot.custom.util.Custom.CUSTOM_INFO;
import static com.chatbot.custom.util.CustomConstant.CUSTOM_CODE_INDEX;
import static com.chatbot.custom.util.CustomConstant.EYES;
import static com.chatbot.custom.util.CustomConstant.NECKLACE;
import static com.chatbot.custom.util.CustomConstant.SNOUT;

import com.chatbot.block.service.CustomBlockService;
import com.chatbot.block.service.CustomForwardBlockService;
import com.chatbot.custom.dto.CustomDto.CustomAToBButton;
import com.chatbot.custom.dto.CustomDto.CustomAToBOutput;
import com.chatbot.custom.dto.CustomDto.CustomAToBResponseDto;
import com.chatbot.custom.dto.CustomDto.CustomAToBTemplate;
import com.chatbot.custom.dto.CustomDto.CustomAToBTextCard;
import com.chatbot.custom.dto.CustomDto.CustomImageResponseDto;
import com.chatbot.custom.dto.CustomDto.CustomInfoDto;
import com.chatbot.custom.dto.CustomDto.ImageOutput;
import com.chatbot.custom.dto.CustomDto.ImageTemplate;
import com.chatbot.custom.dto.CustomDto.Output;
import com.chatbot.custom.dto.CustomDto.QuickReply;
import com.chatbot.custom.dto.CustomDto.ResponseDto;
import com.chatbot.custom.dto.CustomDto.SimpleImage;
import com.chatbot.custom.dto.CustomDto.Template;
import com.chatbot.custom.dto.CustomDto.TextCard;
import com.chatbot.custom.entity.Custom;
import com.chatbot.user.entity.UserEp00;
import com.chatbot.user.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomService {
    private final Map<String, String> LABEL = new HashMap<>() {{
        put(EYES, "안경 선택");
        put(SNOUT, "목걸이 선택");
        put(NECKLACE, "포즈 선택");
    }};
    private final Map<String, String> CATEGORY_TEXT = new HashMap<>() {{
        put(EYES, "눈");
        put(SNOUT, "주둥이");
        put(NECKLACE, "목걸이");
    }};

    private final UserService userService;
    //    private final CustomRepository customRepository;
//    private final CustomQuerydslRepository customRepository;
    private final CustomBlockService customBlockService;
    private final CustomForwardBlockService customForwardBlockService;

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
        Integer result = findCustomByCategoryAndType(custom.getCategory(), custom.getType());
        if (result == null) {
            return "error";
        }
        return String.valueOf(result);
    }

//    private String findCustomImageName(Custom custom) {
//        Custom result = findCustomByCategoryAndType(custom.getCategory(), custom.getType());
//        if (result == null) {
//            return "error";
//        }
//        return String.valueOf(result.getValue());
//    }

    private Integer findCustomByCategoryAndType(String category, String type) {
        return CUSTOM_INFO.get(new CustomInfoDto(category, type));
    }

//    private Custom findCustomByCategoryAndType(String category, String type) {
//        return customRepository.findByCategoryAndAndType(category, type).orElse(null);
//    }

    // 응답 DTO 생성
    public ResponseDto openCustom(UserEp00 userInfo, int customLoc) {
        UserEp00 user = userService.findUserByIdOrderByCustom(userInfo.getFirstId());
        if (user != null) {
            int customCode = user.getCustom();
            customCode ^= (1 << (2 - customLoc));
            user.setCustom(customCode);
            userService.updateUser(user);
        }
//        if (user != null && user.isDlc()) {
//            int customCode = user.getCustom();
//            if ((customCode & (1 << (2 - customLoc))) == 0) {
//                customCode ^= (1 << (2 - customLoc));
//                user.setCustom(customCode);
//                userService.updateUser(user);
//            }
//        }

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

    public CustomAToBResponseDto makeEachCategoryImage(UserEp00 userInfo, Custom customInfo) {
        UserEp00 user = userService.findUserByIdOrderByCustom(userInfo.getFirstId());
        int customCode = user.getCustom();
        String blockId = customForwardBlockService.findBlockId(customInfo.getCategory(),
                (customCode & (1 << CUSTOM_CODE_INDEX.get(customInfo.getCategory()))) != 0);

        return makeCustomAToBResponse(CATEGORY_TEXT.get(customInfo.getCategory()), customInfo.getType(),
                LABEL.get(customInfo.getCategory()), blockId);
    }

    private CustomAToBResponseDto makeCustomAToBResponse(String category, String type, String label, String blockId) {
        List<CustomAToBOutput> outputs = new ArrayList<>();
        CustomAToBOutput output = new CustomAToBOutput(makeTextCard(category, type, label, blockId));
        outputs.add(output);
        CustomAToBTemplate template = new CustomAToBTemplate(Collections.unmodifiableList(outputs));
        return new CustomAToBResponseDto(template);
    }

    private CustomAToBTextCard makeTextCard(String category, String type, String label, String blockId) {
        List<CustomAToBButton> buttons = new ArrayList<>();
        buttons.add(makeButton(label, blockId));
        return new CustomAToBTextCard(category, type, Collections.unmodifiableList(buttons));
    }

    private CustomAToBButton makeButton(String label, String blockId) {
        return new CustomAToBButton(label, blockId);
    }
}
