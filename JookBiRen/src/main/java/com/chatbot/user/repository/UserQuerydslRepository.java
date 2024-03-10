package com.chatbot.user.repository;

import static com.chatbot.user.entity.QUser.user;

import com.chatbot.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserQuerydslRepository {
    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public UserQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(this.em);
    }

    public void save(User user) {
        em.persist(user);
    }

    public Optional<User> findByCode(String code) {
        User result = queryFactory
                .selectFrom(user)
                .where(user.code.eq(code))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public List<User> findAllByFirstId(String kakaoId) {
        List<User> users = queryFactory
                .selectFrom(user)
                .where(user.firstId.eq(kakaoId))
                .fetch();

        if (users == null || users.isEmpty()) {
            return null;
        }
        return users;
    }

    public List<User> findAllByFirstIdOrderByCustomDesc(String kakaoId) {
        List<User> users = queryFactory
                .selectFrom(user)
                .where(user.firstId.eq(kakaoId))
                .orderBy(user.custom.desc())
                .fetch();

        if (users == null || users.isEmpty()) {
            return null;
        }
        return users;
    }
}
