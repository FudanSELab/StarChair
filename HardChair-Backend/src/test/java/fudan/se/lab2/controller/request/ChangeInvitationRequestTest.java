package fudan.se.lab2.controller.request;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ChangeInvitationRequestTest {
    @Test
    @Transactional
    void testChangeInvitationRequest() {
        String[] topics = {"Science"};
        ChangeInvitationRequest changeInvitationRequest = new ChangeInvitationRequest();
        ChangeInvitationRequest changeInvitationRequest1 = new ChangeInvitationRequest("1", "pass", topics);
        changeInvitationRequest1.setInvitationStatus("reject");
        assertThat(changeInvitationRequest1.getInvitationStatus(), is("reject"));
        changeInvitationRequest1.setTopics(topics);
        assertThat(changeInvitationRequest1.getTopics(), is(topics));
    }
}