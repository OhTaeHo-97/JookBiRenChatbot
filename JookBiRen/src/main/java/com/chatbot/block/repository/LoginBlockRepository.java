package com.chatbot.block.repository;

import com.chatbot.block.entity.LoginBlock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginBlockRepository extends JpaRepository<LoginBlock, Long> {
    Optional<LoginBlock> findByIsSuccessfulLogin(boolean isSuccessfulLogin);

    Optional<LoginBlock> findByIsSuccessfulLoginAndIsTutorial(boolean isSuccessfulLogin, boolean isTutorial);
}
