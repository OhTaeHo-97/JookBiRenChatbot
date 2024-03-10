package com.chatbot.custom.repository;

import static com.chatbot.custom.entity.QCustom.custom;

import com.chatbot.custom.entity.Custom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CustomQuerydslRepository {
    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public CustomQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(this.em);
    }

    public Optional<Custom> findByCategoryAndAndType(String category, String type) {
        Custom result = queryFactory
                .selectFrom(custom)
                .where(
                        custom.category.eq(category),
                        custom.type.eq(type)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
