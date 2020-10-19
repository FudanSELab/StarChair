package fudan.se.lab2.domain;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TopicTest {
    @Test
    @Transactional
    void testTopic() {
        Topic topic = new Topic();
        Topic topic1 = new Topic("topic");
        Article article = new Article();
        topic.setArticle(article);
        assertThat(topic.getArticle(), is(article));
        topic.setTopic("topic");
        assertThat(topic.getTopic(), is("topic"));
    }
}