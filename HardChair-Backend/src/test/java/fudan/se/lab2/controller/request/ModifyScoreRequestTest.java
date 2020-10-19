package fudan.se.lab2.controller.request;

import fudan.se.lab2.domain.Score;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ModifyScoreRequestTest {
    @Test
    @Transactional
    void testModifyScoreRequest() {
        ModifyScoreRequest modifyScoreRequest = new ModifyScoreRequest();
        ModifyScoreRequest modifyScoreRequest1 = new ModifyScoreRequest("username", "1", "1", "c", "c", "m");
        modifyScoreRequest.setUsername("user");
        assertThat(modifyScoreRequest.getUsername(), is("user"));
        modifyScoreRequest.setArticleId("2");
        assertThat(modifyScoreRequest.getArticleId(), is("2"));
        modifyScoreRequest.setComment("c");
        assertThat(modifyScoreRequest.getComment(), is("c"));
        modifyScoreRequest.setConfidence("c");
        assertThat(modifyScoreRequest.getConfidence(), is("c"));
        modifyScoreRequest.setModifyStatus("m");
        assertThat(modifyScoreRequest.getModifyStatus(), is("m"));
        modifyScoreRequest.setScore("s");
        assertThat(modifyScoreRequest.getScore(), is("s"));
    }
}