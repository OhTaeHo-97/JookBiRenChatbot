package com.chatbot.block.repository;

import com.chatbot.block.entity.CustomForwardBlock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomForwardBlockRepository extends JpaRepository<CustomForwardBlock, Long> {
    Optional<CustomForwardBlock> findByCategoryAndIsPossible(String category, boolean isPossible);
}
