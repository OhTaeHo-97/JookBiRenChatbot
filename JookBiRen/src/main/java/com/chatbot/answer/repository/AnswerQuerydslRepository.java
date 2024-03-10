package com.chatbot.answer.repository;

import static com.chatbot.answer.entity.QAnswer.answer1;

import com.chatbot.answer.entity.Answer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class AnswerQuerydslRepository {
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public AnswerQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(this.em);
    }

    public Optional<Answer> findByEpisodeAndQuizNumberAndAnswer(int episode, int quizNumber, String answer) {
        Answer result = queryFactory
                .selectFrom(answer1)
                .where(
                        answer1.episode.eq(episode),
                        answer1.quizNumber.eq(quizNumber),
                        answer1.answer.eq(answer)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
