package com.chatbot.block.service;

import com.chatbot.block.entity.CustomBlock;
import com.chatbot.block.repository.CustomBlockQuerydslRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomBlockService {
    //    private final CustomBlockRepository customBlockRepository;
    private final CustomBlockQuerydslRepository customBlockRepository;

    public String findCustomBlockId(boolean isInitial) {
        CustomBlock customBlock = customBlockRepository.findByIsInitial(isInitial).orElse(null);
        if (customBlock == null) {
            return "";
        }
        return customBlock.getBlockId();
    }
}
