package com.chatbot.block.repository;

import static com.chatbot.block.entity.QQuizBlock.quizBlock;

import com.chatbot.block.entity.QuizBlock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class QuizBlockQuerydslRepository {
    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public QuizBlockQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(this.em);
    }

    public Optional<QuizBlock> findByEpisodeAndQuizNumberAndIsCorrectAndIsAnswer(int episode, int quizNumber,
                                                                                 boolean isCorrect, boolean isAnswer) {
        QuizBlock result = queryFactory
                .selectFrom(quizBlock)
                .where(
                        quizBlock.episode.eq(episode),
                        quizBlock.quizNumber.eq(quizNumber),
                        quizBlock.isCorrect.eq(isCorrect),
                        quizBlock.isAnswer.eq(isAnswer)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
