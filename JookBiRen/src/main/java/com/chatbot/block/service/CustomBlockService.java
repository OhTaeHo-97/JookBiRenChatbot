package com.chatbot.block.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomBlockService {
    //    private final CustomBlockRepository customBlockRepository;
//    private final CustomBlockQuerydslRepository customBlockRepository;

    public String findCustomBlockId(boolean isInitial) {
        if (isInitial) {
            return "6762dd757c842c4d39423b2c";
        }
        return "652fc1bad134aa37160bdb6f";
    }

//    public String findCustomBlockId(boolean isInitial) {
//        CustomBlock customBlock = customBlockRepository.findByIsInitial(isInitial).orElse(null);
//        if (customBlock == null) {
//            return "";
//        }
//        return customBlock.getBlockId();
//    }
}
