package fudan.se.lab2.domain;

import fudan.se.lab2.repository.InvitationRepository;
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
class InvitationTest {
    @Autowired
    private InvitationRepository invitationRepository;

    @Test
    @Transactional
    void testInvitation() {
        Invitation invitation = new Invitation("inviter", "invitee", "2", "false");
        invitationRepository.save(invitation);
        invitation.setInviter("inviter1");
        assertThat(invitation.getInviter(), is("inviter1"));
        invitation.setInvitee("invitee1");
        assertThat(invitation.getInvitee(), is("invitee1"));
        invitation.setMeetingId("10");
        assertThat(invitation.getMeetingId(), is("10"));
        invitation.setInvitationStatus("pass");
        assertThat(invitation.getInvitationStatus(), is("pass"));
    }
}