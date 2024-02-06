package com.chatbot.block.service;

import com.chatbot.block.entity.Block;
import com.chatbot.block.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;

    public String findBlockId(int episode, int quizNumber, boolean isCorrect, boolean isAnswer) {
        Block block = validateBlock(episode, quizNumber, isCorrect, isAnswer);
        if (block == null) {
            return new String();
        }
        return block.getBlockId();
    }

    private Block validateBlock(int episode, int quizNumber, boolean isCorrect, boolean isAnswer) {
        return blockRepository.findByEpisodeAndQuizNumberAndIsCorrectAndIsAnswer(episode, quizNumber, isCorrect,
                isAnswer).orElse(null);
    }
}
