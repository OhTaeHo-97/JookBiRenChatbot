package com.chatbot.block.repository;

import static com.chatbot.block.entity.QCustomBlock.customBlock;

import com.chatbot.block.entity.CustomBlock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CustomBlockQuerydslRepository {
    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public CustomBlockQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(this.em);
    }

    public Optional<CustomBlock> findByIsInitial(boolean isInitial) {
        CustomBlock result = queryFactory
                .selectFrom(customBlock)
                .where(customBlock.isInitial.eq(isInitial))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
