package fudan.se.lab2.domain;

import fudan.se.lab2.repository.ScoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ScoreTest {
    @Autowired
    private ScoreRepository scoreRepository;

    @Test
    @Transactional
    void testScore() {
        User user = new User();
        Score score1 = new Score(user.getId());
        Score score2 = new Score(user.getId(), "score", "comment", "c", "reviewed", "1");
        score2.setPcmember_id(1L);
        assertThat(score2.getPcmember_id(), is(1L));
        score2.setScore("score");
        assertThat(score2.getScore(), is("score"));
        score2.setConfidence("confidence");
        assertThat(score2.getConfidence(), is("confidence"));
        score2.setComment("comment");
        assertThat(score2.getComment(), is("comment"));
        score2.setReviewStatus("false");
        assertThat(score2.getReviewStatus(), is("false"));
        Article article = new Article();
        score2.setArticle2(article);
        assertThat(score2.getArticle2(), is(article));
        score2.setOldId("1");
        assertThat(score2.getOldId(), is("1"));
    }
}