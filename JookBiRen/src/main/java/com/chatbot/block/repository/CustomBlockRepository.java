package com.chatbot.block.repository;

import com.chatbot.block.entity.CustomBlock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomBlockRepository extends JpaRepository<CustomBlock, Long> {
    Optional<CustomBlock> findByIsInitial(boolean isInitial);
}
