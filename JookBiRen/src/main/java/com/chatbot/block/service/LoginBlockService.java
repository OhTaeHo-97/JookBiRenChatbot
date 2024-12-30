package com.chatbot.block.service;

import static com.chatbot.block.utils.LoginBlock.LOGIN_BLOCK;

import com.chatbot.block.entity.LoginBlock;
import com.chatbot.dto.LoginBlockDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class LoginBlockService {
    private static final Logger log = LoggerFactory.getLogger(LoginBlockService.class);
    //    private final LoginBlockRepository loginBlockRepository;
//    private final LoginBlockQuerydslRepository loginBlockRepository;

    public List<String> findLoginBlockByLogin(boolean isSuccessfulLogin, boolean isStory) {
        return getLoginBlocks(isSuccessfulLogin, isStory);
    }

//    public List<String> findLoginBlockByLogin(boolean isSuccessfulLogin, boolean isStory) {
//        List<LoginBlock> loginBlocks = getLoginBlocks(isSuccessfulLogin, isStory);
//        return extractBlockIdInLoginBlocks(loginBlocks);
//    }

    private List<String> getLoginBlocks(boolean isSuccessfulLogin, boolean isStory) {
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

//    private List<LoginBlock> getLoginBlocks(boolean isSuccessfulLogin, boolean isStory) {
//        if (isSuccessfulLogin && isStory) {
//            return getSuccessfulLoginBlocks();
//        }
//        if (isSuccessfulLogin && !isStory) {
//            return getSuccessfulCustomLoginBlocks();
//        }
//        if (!isSuccessfulLogin && !isStory) {
//            return getUnsuccessfulCustomLoginBlocks();
//        }
//        return getUnsuccessfulLoginBlocks();
//    }

    private List<String> getSuccessfulLoginBlocks() {
        List<String> loginBlocks = new ArrayList<>();
        String loginBlock = LOGIN_BLOCK.get(new LoginBlockDto(true, false, true));
        String tutorialLoginBlock = LOGIN_BLOCK.get(new LoginBlockDto(true, true, true));
        loginBlocks.add(loginBlock);
        loginBlocks.add(tutorialLoginBlock);
        return loginBlocks;
    }

//    private List<LoginBlock> getSuccessfulLoginBlocks() {
//        List<LoginBlock> loginBlocks = new ArrayList<>();
//        LoginBlock loginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsTutorialAndIsStory(true, false, true)
//                .orElse(null);
//        LoginBlock tutorialLoginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsTutorialAndIsStory(true, true,
//                true).orElse(null);
//        loginBlocks.add(loginBlock);
//        loginBlocks.add(tutorialLoginBlock);
//        return loginBlocks;
//    }

    private List<String> getSuccessfulCustomLoginBlocks() {
        List<String> loginBlocks = new ArrayList<>();
        loginBlocks.add(LOGIN_BLOCK.get(new LoginBlockDto(true, false, false)));
        return loginBlocks;
    }

//    private List<LoginBlock> getSuccessfulCustomLoginBlocks() {
//        List<LoginBlock> loginBlocks = new ArrayList<>();
//        LoginBlock loginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsStory(true, false).orElse(null);
//        loginBlocks.add(loginBlock);
//        return loginBlocks;
//    }

    private List<String> getUnsuccessfulLoginBlocks() {
        List<String> loginBlocks = new ArrayList<>();
        loginBlocks.add(LOGIN_BLOCK.get(new LoginBlockDto(false, false, true)));
        return loginBlocks;
    }

//    private List<LoginBlock> getUnsuccessfulLoginBlocks() {
//        List<LoginBlock> loginBlocks = new ArrayList<>();
//        LoginBlock loginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsStory(false, true).orElse(null);
//        loginBlocks.add(loginBlock);
//        return loginBlocks;
//    }

    private List<String> getUnsuccessfulCustomLoginBlocks() {
        List<String> loginBlocks = new ArrayList<>();
        loginBlocks.add(LOGIN_BLOCK.get(new LoginBlockDto(false, false, false)));
        return loginBlocks;
    }

//    private List<LoginBlock> getUnsuccessfulCustomLoginBlocks() {
//        List<LoginBlock> loginBlocks = new ArrayList<>();
//        LoginBlock loginBlock = loginBlockRepository.findByIsSuccessfulLoginAndIsStory(false, false).orElse(null);
//        loginBlocks.add(loginBlock);
//        return loginBlocks;
//    }

    private List<String> extractBlockIdInLoginBlocks(List<LoginBlock> loginBlocks) {
        System.out.println(loginBlocks);
        return loginBlocks.stream().map(loginBlock -> loginBlock.getBlockId()).collect(Collectors.toList());
    }
}
