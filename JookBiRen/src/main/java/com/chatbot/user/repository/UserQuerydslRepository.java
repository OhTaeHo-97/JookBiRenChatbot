package com.chatbot.user.repository;

import static com.chatbot.user.entity.QUserEp00.userEp00;

import com.chatbot.user.entity.UserEp00;
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

    public void save(UserEp00 userEp00) {
        em.persist(userEp00);
    }

    public Optional<UserEp00> findByCode(String code) {
        UserEp00 result = queryFactory
                .selectFrom(userEp00)
                .where(userEp00.code.eq(code))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public List<UserEp00> findAllByFirstId(String kakaoId) {
        List<UserEp00> userEp00s = queryFactory
                .selectFrom(userEp00)
                .where(userEp00.firstId.eq(kakaoId))
                .fetch();

        if (userEp00s == null || userEp00s.isEmpty()) {
            return null;
        }
        return userEp00s;
    }

    public List<UserEp00> findAllByFirstIdOrderByCustomDesc(String kakaoId) {
        List<UserEp00> userEp00s = queryFactory
                .selectFrom(userEp00)
                .where(userEp00.firstId.eq(kakaoId))
                .orderBy(userEp00.custom.desc())
                .fetch();

        if (userEp00s == null || userEp00s.isEmpty()) {
            return null;
        }
        return userEp00s;
    }
}
