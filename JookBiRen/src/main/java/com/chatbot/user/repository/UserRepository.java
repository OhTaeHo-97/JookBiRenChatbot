package com.chatbot.user.repository;

import com.chatbot.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCode(String code);

    List<User> findAllByFirstId(String kakaoId);

    List<User> findAllByFirstIdOrderByCustomDesc(String kakaoId);
}
