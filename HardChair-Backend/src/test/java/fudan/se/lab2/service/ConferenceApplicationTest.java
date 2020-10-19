package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ChangeApplicationStatusRequest;
import fudan.se.lab2.controller.request.MeetingRequest;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConferenceApplicationTest {
    @Autowired
    private ConferenceApplicationService conferenceApplicationService;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private AllMeetingsService allMeetingsService;
    @Autowired
    private ChairService chairService;
    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void testConferenceApplication() {
        //1. 测试申请会议
        String[] topic = {"science", "math", "computer"};
        {   //会议申请1
            MeetingRequest meetingRequest1 = new MeetingRequest("conferenceTest1", "tC",
                    "2020-4-10", "shanghai", "2020-5-10",
                    "2020-5-15", "registerTest", topic);
            Meeting newMeeting1 = conferenceApplicationService.applyConference(meetingRequest1);
            assertThat(newMeeting1, notNullValue());
            assertThat(newMeeting1.getFullName(), is("conferenceTest1"));
        }
        {   //会议申请2
            MeetingRequest meetingRequest2 = new MeetingRequest("conferenceTest2", "tC",
                    "2020-4-10", "shanghai", "2020-5-10",
                    "2020-5-15", "registerTest", topic);
            Meeting newMeeting2 = conferenceApplicationService.applyConference(meetingRequest2);
            assertThat(newMeeting2, notNullValue());
            assertThat(newMeeting2.getFullName(), is("conferenceTest2"));
        }

        //2. 测试管理员查看待审核会议
        assertThat(conferenceApplicationService.getUncheckedConference(), notNullValue());

        //3. 测试管理员通过会议申请
        Meeting newMeeting1 = meetingRepository.findByFullName("conferenceTest1");
        ChangeApplicationStatusRequest statusRequestPass = new ChangeApplicationStatusRequest(newMeeting1.getId().toString(), "pass");
        conferenceApplicationService.changeApplicationStatus(statusRequestPass);
        assertThat(meetingRepository.findByFullName("conferenceTest1").getIsVarified(), is("pass")); //检查是否修改为pass

        //4. 测试管理员拒绝会议申请
        Meeting newMeeting2 = meetingRepository.findByFullName("conferenceTest2");
        ChangeApplicationStatusRequest statusRequestReject = new ChangeApplicationStatusRequest(newMeeting2.getId().toString(), "reject");
        conferenceApplicationService.changeApplicationStatus(statusRequestReject);
        assertThat(meetingRepository.findByFullName("conferenceTest2").getIsVarified(), is("reject")); //检查是否修改为reject

        //5. 测试我申请的会议
        assertThat(userCenterService.myMeetingApplication("registerTest"),notNullValue());

        //6. 测试所有通过的会议
        assertThat(allMeetingsService.allPassedMeetings(),notNullValue());

        //7. 测试我参与的会议及角色
        assertThat(userCenterService.myMeeting("registerTest"),notNullValue());
        assertThat(userCenterService.getUser_MeetingAuthorityList(newMeeting1.getId().toString(), "registerTest").get(0).getAuthority(), is("ROLE_CHAIR"));

        //8. 测试开启投稿
        chairService.changeSubmitStatus(newMeeting1.getId().toString(), "inSubmit");
        assertThat(meetingRepository.findByFullName("conferenceTest1").getSubmitStatus(), is("inSubmit"));
    }
}
