package com.chatbot.block.service;

import static com.chatbot.block.utils.CustomForwardBlock.CUSTOM_FORWARD_BLOCKS;

import com.chatbot.dto.CustomForwardBlockDto;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomForwardBlockService {
    //    private final CustomForwardBlockRepository customForwardBlockRepository;
//    private final CustomForwardBlockQuerydslRepository customForwardBlockRepository;

    public String findBlockId(String category, boolean isPossible) {
//        CustomForwardBlock customForwardBlock = validateCustomForwardBlock(category, isPossible);
//        if (customForwardBlock == null) {
//            return "";
//        }
//        return customForwardBlock.getBlockId();
        String blockId = validateCustomForwardBlock(category, isPossible);
        if (blockId == null) {
            return "";
        }
        return blockId;
    }

//    private CustomForwardBlock validateCustomForwardBlock(String category, boolean isPossible) {
//        return customForwardBlockRepository.findByCategoryAndIsPossible(category, isPossible).orElse(null);
//    }

    private String validateCustomForwardBlock(String category, boolean isPossible) {
        return CUSTOM_FORWARD_BLOCKS.get(new CustomForwardBlockDto(category, isPossible));
    }
}
