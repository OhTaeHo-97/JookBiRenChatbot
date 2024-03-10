package com.chatbot.block.repository;

import static com.chatbot.block.entity.QCustomForwardBlock.customForwardBlock;

import com.chatbot.block.entity.CustomForwardBlock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CustomForwardBlockQuerydslRepository {
    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public CustomForwardBlockQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(this.em);
    }

    public Optional<CustomForwardBlock> findByCategoryAndIsPossible(String category, boolean isPossible) {
        CustomForwardBlock result = queryFactory
                .selectFrom(customForwardBlock)
                .where(
                        customForwardBlock.category.eq(category),
                        customForwardBlock.isPossible.eq(isPossible)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
