package com.chatbot.custom.repository;

import com.chatbot.custom.entity.Custom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomRepository extends JpaRepository<Custom, Long> {
    Optional<Custom> findByCategoryAndAndType(String category, String type);
}
