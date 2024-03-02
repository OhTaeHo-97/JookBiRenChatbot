package com.chatbot.block.service;

import com.chatbot.block.entity.LoginBlock;
import com.chatbot.block.repository.LoginBlockRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class LoginBlockService {
    private final LoginBlockRepository loginBlockRepository;

    public List<String> findLoginBlockByLogin(boolean isSuccessfulLogin, boolean isStory) {
        List<LoginBlock> loginBlocks = getLoginBlocks(isSuccessfulLogin, isStory);
        return extractBlockIdInLoginBlocks(loginBlocks);
    }

    private List<LoginBlock> getLoginBlocks(boolean isSuccessfulLogin, boolean isStory) {
        if (isSuccessfulLogin && isStory) {
            return getSuccessfulLoginBlocks();
        }
        if (isSuccessfulLogin && !isStory) {
            return getSuccessfulCustomLoginBlocks();
        }
        if (!isSuccessfulLogin && !isStory) {
            return getUnsuccessfulCustomLoginBlocks();
        }
        return getUnsuccessfulLoginBlocks();
    }

    private List<LoginBlock> getSuccessfulLoginBlocks() {
        List<LoginBlock> loginBlocks = new ArrayList<>();
        LoginBlock loginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsTutorialAndIsStory(true, false, true)
                .orElse(null);
        LoginBlock tutorialLoginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsTutorialAndIsStory(true, true,
                true).orElse(null);
        loginBlocks.add(loginBlock);
        loginBlocks.add(tutorialLoginBlock);
        return loginBlocks;
    }

    private List<LoginBlock> getSuccessfulCustomLoginBlocks() {
        List<LoginBlock> loginBlocks = new ArrayList<>();
        LoginBlock loginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsStory(true, false).orElse(null);
        loginBlocks.add(loginBlock);
        return loginBlocks;
    }

    private List<LoginBlock> getUnsuccessfulLoginBlocks() {
        List<LoginBlock> loginBlocks = new ArrayList<>();
        LoginBlock loginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsStory(false, true).orElse(null);
        loginBlocks.add(loginBlock);
        return loginBlocks;
    }

    private List<LoginBlock> getUnsuccessfulCustomLoginBlocks() {
        List<LoginBlock> loginBlocks = new ArrayList<>();
        LoginBlock loginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsStory(false, false).orElse(null);
        loginBlocks.add(loginBlock);
        return loginBlocks;
    }

    private List<String> extractBlockIdInLoginBlocks(List<LoginBlock> loginBlocks) {
        System.out.println(loginBlocks);
        return loginBlocks.stream().map(loginBlock -> loginBlock.getBlockId()).collect(Collectors.toList());
    }
}
