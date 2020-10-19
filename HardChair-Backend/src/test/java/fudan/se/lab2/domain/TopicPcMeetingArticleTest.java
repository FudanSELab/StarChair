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
class TopicPcMeetingArticleTest {
    @Test
    @Transactional
    void testTopicPcMeetingArticle() {
        TopicPcMeetingArticle topicPcMeetingArticle = new TopicPcMeetingArticle();
        Topic topic = new Topic();
        Meeting meeting = new Meeting();
        User pc = new User();
        TopicPcMeetingArticle topicPcMeetingArticle1 = new TopicPcMeetingArticle(topic, pc, meeting);
        topicPcMeetingArticle.setPcmember(pc);
        assertThat(topicPcMeetingArticle.getPcmember(), is(pc));
        topicPcMeetingArticle.setMeeting(meeting);
        assertThat(topicPcMeetingArticle.getMeeting(), is(meeting));
        topicPcMeetingArticle.setTopic(topic);
        assertThat(topicPcMeetingArticle.getTopic(), is(topic));
    }
}