package fudan.se.lab2.domain;

import fudan.se.lab2.repository.Sub_DiscussionRepository;
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
class Sub_DiscussionTest {
    @Autowired
    private Sub_DiscussionRepository subDiscussionRepository;

    @Test
    @Transactional
    void testSub_Discussion() {
        Sub_Discussion sub_discussion1 = new Sub_Discussion("11", "content", "username", "first");
        Sub_Discussion sub_discussion2 = new Sub_Discussion("11", "content", "username", "2", "reviewee", "first");
        subDiscussionRepository.save(sub_discussion1);
        subDiscussionRepository.save(sub_discussion2);
        sub_discussion1.setArticleId("12");
        assertThat(sub_discussion1.getArticleId(), is("12"));
        sub_discussion1.setContent("content1");
        assertThat(sub_discussion1.getContent(), is("content1"));
        sub_discussion1.setDiscussStatus("second");
        assertThat(sub_discussion1.getDiscussStatus(), is("second"));
        sub_discussion1.setCreateTime("createTime");
        assertThat(sub_discussion1.getCreateTime(), is("createTime"));
        sub_discussion1.setFatherId("3");
        assertThat(sub_discussion1.getFatherId(), is("3"));
        sub_discussion1.setReviewee("review");
        assertThat(sub_discussion1.getReviewee(), is("review"));
        sub_discussion1.setSubId(Long.parseLong("3"));
        assertThat(sub_discussion1.getSubId(), is(Long.parseLong("3")));
        sub_discussion1.setUsername("user");
        assertThat(sub_discussion1.getUsername(), is("user"));
        Main_Discussion mainDiscussion = new Main_Discussion();
        sub_discussion1.setMain_discussion(mainDiscussion);
        assertThat(sub_discussion1.getMain_discussion(),is(mainDiscussion));
    }

}