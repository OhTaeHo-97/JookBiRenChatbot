package com.chatbot.block.service;

import com.chatbot.block.entity.CustomForwardBlock;
import com.chatbot.block.repository.CustomForwardBlockRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomForwardBlockService {
    private final CustomForwardBlockRepository customForwardBlockRepository;

    public String findBlockId(String category, boolean isPossible) {
        CustomForwardBlock customForwardBlock = validateCustomForwardBlock(category, isPossible);
        if (customForwardBlock == null) {
            return "";
        }
        return customForwardBlock.getBlockId();
    }

    private CustomForwardBlock validateCustomForwardBlock(String category, boolean isPossible) {
        return customForwardBlockRepository.findByCategoryAndIsPossible(category, isPossible).orElse(null);
    }
}
