package com.chatbot.block.repository;

import static com.chatbot.block.entity.QLoginBlock.loginBlock;

import com.chatbot.block.entity.LoginBlock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class LoginBlockQuerydslRepository {
    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public LoginBlockQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(this.em);
    }

    public Optional<LoginBlock> findByIsSuccessfulLoginAndIsTutorialAndIsStory(boolean isSuccessfulLogin,
                                                                               boolean isTutorial, boolean isStory) {
        LoginBlock result = queryFactory
                .selectFrom(loginBlock)
                .where(
                        loginBlock.isSuccessfulLogin.eq(isSuccessfulLogin),
                        loginBlock.isTutorial.eq(isTutorial),
                        loginBlock.isStory.eq(isStory)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public Optional<LoginBlock> findByIsSuccessfulLoginAndIsStory(boolean isSuccessfulLogin, boolean isStory) {
        LoginBlock result = queryFactory
                .selectFrom(loginBlock)
                .where(
                        loginBlock.isSuccessfulLogin.eq(isSuccessfulLogin),
                        loginBlock.isStory.eq(isStory)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
