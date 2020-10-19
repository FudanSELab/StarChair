package fudan.se.lab2.service;

import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.service.ChairService;
import fudan.se.lab2.service.UserCenterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvitationTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChairService chairService;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private InvitationRepository invitationRepository;

    @Test
    public void testInvitation() {
        //1. 测试通过fullname查找用户
        User pc1 = new User("pc1", "pc", "password",
                "123@pc1.com", "fudan", "shanghai", new HashSet<>());
        userRepository.save(pc1);
        User pc2 = new User("pc2", "pc", "password",
                "123@pc2.com", "fudan", "shanghai", new HashSet<>());
        userRepository.save(pc2);
        User pc3 = new User("pc3", "pc", "password",
                "123@pc3.com", "fudan", "shanghai", new HashSet<>());
        userRepository.save(pc3);
        User rejectPc = new User("rejectPc", "rejectPc", "password",
                "123@rejectPc.com", "fudan", "shanghai", new HashSet<>());
        userRepository.save(rejectPc);
        assertThat(chairService.searchForUser("pc"), notNullValue());

        //2. 测试发送邀请
        List<User> inviteeUser = userRepository.findByFullName("pc");
        String[] invited = new String[3];
        for (int i = 0; i < 3; i++) {
            invited[i] = inviteeUser.get(i).getId().toString();
        }
        Meeting meeting = meetingRepository.findByFullName("conferenceTest1");
        assertThat(chairService.sendInvitation(meeting.getId().toString(), "registerTest", invited), is("success"));

        //3. 测试我发出的邀请
        assertThat(userCenterService.invitationISent("registerTest"), notNullValue());

        //4. 测试我收到的邀请
        User invitee = userRepository.findByUsername("pc1");
        assertThat(userCenterService.invitationIReceived(invitee.getId().toString()), notNullValue());

        //5. 测试修改邀请状态
        String[] topics = {"science", "math", "computer"};
        List<User> userList = userRepository.findByFullName("pc");
        for (User user : userList) { //通过邀请
            Optional<Invitation> optionalInvitation = invitationRepository.findByInviterAndInviteeAndMeetingID("registerTest",
                    user.getId().toString(), meetingRepository.findByFullName("conferenceTest1").getId().toString());
            if (optionalInvitation.isPresent()) {
                Invitation invitation = optionalInvitation.get();
                userCenterService.changeInvitationStatus(invitation.getId().toString(), "pass", topics);
                assertThat(userCenterService.getUser_MeetingAuthorityList(meeting.getId().toString(), user.getUsername()).get(0).getAuthority(), is("ROLE_PCMEMBER"));
            }
        }
        User rejectUser = userRepository.findByUsername("rejectPc");
        Optional<Invitation> optionalInvitation = invitationRepository.findByInviterAndInviteeAndMeetingID("registerTest",
                rejectUser.getId().toString(), meetingRepository.findByFullName("conferenceTest1").getId().toString());
        if (optionalInvitation.isPresent()) { //拒绝邀请
            Invitation invitation = optionalInvitation.get();
            userCenterService.changeInvitationStatus(invitation.getId().toString(), "reject", topics);
            assertThat(userCenterService.getUser_MeetingAuthorityList(meeting.getId().toString(), rejectUser.getUsername()).get(0).getAuthority(), is(null));
        }
    }
}
