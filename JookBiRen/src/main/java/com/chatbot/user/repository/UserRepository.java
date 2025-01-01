package com.chatbot.user.repository;

import com.chatbot.user.entity.UserEp00;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEp00, Long> {
    Optional<UserEp00> findByCode(String code);

    List<UserEp00> findAllByFirstId(String kakaoId);

    List<UserEp00> findAllByFirstIdOrderByCustomDesc(String kakaoId);
}
