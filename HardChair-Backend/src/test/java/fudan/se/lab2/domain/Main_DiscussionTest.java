package fudan.se.lab2.domain;


import fudan.se.lab2.repository.Main_DiscussionRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class Main_DiscussionTest {
    @Autowired
    private Main_DiscussionRepository mainDiscussionRepository;

    @Test
    @Transactional
    void testMain_Discussion() {
        Main_Discussion mainDiscussion = new Main_Discussion("1", "username", "first");
        Main_Discussion mainDiscussion1 = new Main_Discussion("1", "username", "content", "first");
        mainDiscussion.setMainId(Long.parseLong("1"));
        assertThat(mainDiscussion.getMainId(), is(Long.parseLong("1")));
        mainDiscussion.setContent("content");
        assertThat(mainDiscussion.getContent(), is("content"));
        mainDiscussion.setArticleId("2");
        assertThat(mainDiscussion.getArticleId(), is("2"));
        mainDiscussion.setCreateTime("3");
        assertThat(mainDiscussion.getCreateTime(), is("3"));
        mainDiscussion.setDiscussStatus("second");
        assertThat(mainDiscussion.getDiscussStatus(), is("second"));
        mainDiscussion.setUsername("user");
        assertThat(mainDiscussion.getUsername(), is("user"));
        List<Sub_Discussion> subDiscussions = new ArrayList<>();
        mainDiscussion.setSub_discussions(subDiscussions);
        assertThat(mainDiscussion.getSub_discussions(), is(subDiscussions));
    }
}