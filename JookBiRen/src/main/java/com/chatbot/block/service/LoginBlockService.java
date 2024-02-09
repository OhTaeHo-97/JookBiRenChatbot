package com.chatbot.block.service;

import com.chatbot.block.entity.LoginBlock;
import com.chatbot.block.repository.LoginBlockRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class LoginBlockService {
    private final LoginBlockRepository loginBlockRepository;

    public List<String> findLoginBlockByLogin(boolean isSuccessfulLogin) {
        List<LoginBlock> loginBlocks = getLoginBlocks(isSuccessfulLogin);
        return extractBlockIdInLoginBlocks(loginBlocks);
    }

    private List<LoginBlock> getLoginBlocks(boolean isSuccessfulLogin) {
        if (isSuccessfulLogin) {
            return getSuccessfulLoginBlocks();
        }
        return getUnsuccessfulLoginBlocks();
    }

    private List<LoginBlock> getSuccessfulLoginBlocks() {
        List<LoginBlock> loginBlocks = new ArrayList<>();
        LoginBlock loginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsTutorial(true, false).orElse(null);
        LoginBlock tutorialLoginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsTutorial(true, true)
                .orElse(null);
        loginBlocks.add(loginBlock);
        loginBlocks.add(tutorialLoginBlock);
        return loginBlocks;
    }

    private List<LoginBlock> getUnsuccessfulLoginBlocks() {
        List<LoginBlock> loginBlocks = new ArrayList<>();
        LoginBlock loginBlock = loginBlockRepository.findByIsSuccessfulLogin(false).orElse(null);
        loginBlocks.add(loginBlock);
        return loginBlocks;
    }

    private List<String> extractBlockIdInLoginBlocks(List<LoginBlock> loginBlocks) {
        return loginBlocks.stream().map(loginBlock -> loginBlock.getBlockId()).toList();
    }
}
