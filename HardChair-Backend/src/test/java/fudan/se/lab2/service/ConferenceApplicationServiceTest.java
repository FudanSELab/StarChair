package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ChangeApplicationStatusRequest;
import fudan.se.lab2.controller.request.MeetingRequest;
import fudan.se.lab2.controller.request.ScoreRequest;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ConferenceApplicationServiceTest {
    @Autowired
    private ConferenceApplicationService conferenceApplicationService;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private ChairService chairService;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private AllMeetingsService allMeetingsService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private PcMemberService pcMemberService;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @Transactional
    void testAll() throws Exception {
        Meeting newMeeting = testApplyConference();
        testGetUncheckedConference();
        testMyMeetingApplication();
        testChangeApplicationStatus(newMeeting);
        testAllPassedMeetings();
        testMyMeeting();
        testGetUser_MeetingAuthorityList(newMeeting);
        testChangeSubmitStatus(newMeeting);
        testSearchForUser();
        testSendInvitation();
        testInvitationISent();
        testInvitationIReceived();
        testChangeInvitationStatus();
        testCheckIfChair();
        testStoreArticleContribution();
        testGetContribution();
        testModifyArticleContribution();
        testGetPcList(newMeeting);
        testGetAllArticles(newMeeting);
        testAllotArticle(newMeeting);
        testFinishContribution(newMeeting);
        Map<String, Object> map = testGetAllotedArticle(newMeeting);
        List<Article> articleList = (List<Article>)map.get("articleList");
        testSubmitScore(articleList.get(0));
//        testPublishScores(newMeeting);
    }

    //测试会议申请
    Meeting testApplyConference() {
        String[] topic = {"science"};
        MeetingRequest meetingRequest = new MeetingRequest("meetingRequest", "MR",
                "2020-4-10", "shanghai", "2020-5-10",
                "2020-5-15", "test0", topic);
        Meeting newMeeting = conferenceApplicationService.applyConference(meetingRequest);
        assertThat(newMeeting, notNullValue());
        assertThat(newMeeting.getFullName(), is("meetingRequest"));
        return newMeeting;
    }

    //测试待审核会议
    void testGetUncheckedConference() {
        assertThat(conferenceApplicationService.getUncheckedConference(), notNullValue());
    }

    //测试我申请的会议
    void testMyMeetingApplication() {
        assertThat(userCenterService.myMeetingApplication("test0"), notNullValue());
    }

    //测试会议审核通过或拒绝
    void testChangeApplicationStatus(Meeting newMeeting) {
//        ChangeApplicationStatusRequest statusRequestReject = new ChangeApplicationStatusRequest(newMeeting.getId().toString(), "reject");
//        conferenceApplicationService.changeApplicationStatus(statusRequestReject);
//        assertThat(meetingRepository.findByFullName("meetingRequest").getIsVarified(), is("reject")); //检查是否修改为reject
        ChangeApplicationStatusRequest statusRequestPass = new ChangeApplicationStatusRequest(newMeeting.getId().toString(), "pass");
        conferenceApplicationService.changeApplicationStatus(statusRequestPass);
        assertThat(meetingRepository.findByFullName("meetingRequest").getIsVarified(), is("pass")); //检查是否修改为pass
    }

    //测试所有通过的会议
    void testAllPassedMeetings() {
        assertThat(allMeetingsService.allPassedMeetings(), notNullValue());
    }

    //测试我参与的会议
    void testMyMeeting() {
        assertThat(userCenterService.myMeeting("test0"), notNullValue());
    }

    //测试该会议我的角色列表
    void testGetUser_MeetingAuthorityList(Meeting meeting) {
        assertThat(userCenterService.getUser_MeetingAuthorityList(meeting.getId().toString(), "test0"), notNullValue());
    }

    //测试修改投稿状态
    void testChangeSubmitStatus(Meeting meeting) {
        chairService.changeSubmitStatus(meeting.getId().toString(), "inSubmit");
        assertThat(meetingRepository.findByFullName("meetingRequest").getSubmitStatus(), is("inSubmit"));
    }

    //测试通过fullName查找用户
    void testSearchForUser() {
        User pc1 = new User("pc11", "pcpc", "password",
                "123@pc11.com", "fudan", "shanghai", new HashSet<>());
        userRepository.save(pc1);
        User pc2 = new User("pc22", "pcpc", "password",
                "123@pc22.com", "fudan", "shanghai", new HashSet<>());
        userRepository.save(pc2);
        User pc3 = new User("pc33", "pcpc", "password",
                "123@pc33.com", "fudan", "shanghai", new HashSet<>());
        userRepository.save(pc3);
        assertThat(chairService.searchForUser("pcpc"), notNullValue());
    }

    //测试发送邀请
    void testSendInvitation() {
        List<User> inviteeUser = userRepository.findByFullName("pcpc");
        String[] invited = new String[3];
        for (int i = 0; i < 3; i++) {
            invited[i] = inviteeUser.get(i).getId().toString();
        }
        assertThat(chairService.sendInvitation(meetingRepository.findByFullName("meetingRequest").getId().toString(), "test0", invited), is("success"));
    }

    //测试我发出的邀请
    void testInvitationISent() {
        assertThat(userCenterService.invitationISent("test0"), notNullValue());
    }

    //测试我收到的邀请
    void testInvitationIReceived() {
        User invitee = userRepository.findByUsername("pc11");
        assertThat(userCenterService.invitationIReceived(invitee.getId().toString()), notNullValue());
    }

    //测试修改邀请状态
    void testChangeInvitationStatus() {
        String[] topics = {"science"};
        List<User> userList = userRepository.findByFullName("pcpc");
        for (User user : userList) {
            Optional<Invitation> optionalInvitation = invitationRepository.findByInviterAndInviteeAndMeetingID("test0",
                    user.getId().toString(), meetingRepository.findByFullName("meetingRequest").getId().toString());
            if (optionalInvitation.isPresent()) {
                Invitation invitation = optionalInvitation.get();
                userCenterService.changeInvitationStatus(invitation.getId().toString(), "pass", topics);
            }
        }
    }

    //测试是否是chair
    void testCheckIfChair() {
        assertThat(allMeetingsService.checkIfChair("test0", meetingRepository.findByFullName("meetingRequest").getId().toString()), is("warning"));
        assertThat(allMeetingsService.checkIfChair("pc11", meetingRepository.findByFullName("meetingRequest").getId().toString()), is("success"));
    }

    //测试投稿
    void testStoreArticleContribution() throws Exception {
        File file = new File("C:\\Users\\nqxxz\\Desktop\\MidExam19.pdf");
        File wrongFile = new File("C:\\Users\\nqxxz\\Desktop\\wrong.txt");
        FileInputStream in_file = new FileInputStream(file);
        FileInputStream wrongIn_file = new FileInputStream(wrongFile);
        MultipartFile multi = new MockMultipartFile("MidExam19.pdf", "MidExam19.pdf", "application/pdf", in_file);
        MultipartFile multiWrong = new MockMultipartFile("MidExam19.pdf", wrongIn_file);
        String[] topic = {"science"};
        allMeetingsService.storeArticleContribution(multiWrong, "MidExam19",
                "[{\"name\":\"w\",\"email\":\"qdw@1.co\",\"company\":\"sda\",\"area\":\"asd\"}]",
                "summary", topic, "pc11",
                meetingRepository.findByFullName("meetingRequest").getId().toString(),
                "C:\\Users\\nqxxz\\Downloads\\");
        allMeetingsService.storeArticleContribution(multi, "MidExam19",
                "[{\"name\":\"w\",\"email\":\"qdw@1.co\",\"company\":\"sda\",\"area\":\"asd\"}]",
                "summary", topic, "pc11",
                meetingRepository.findByFullName("meetingRequest").getId().toString(),
                "C:\\Users\\nqxxz\\Downloads\\");
    }

    //测试修改投稿
    void testModifyArticleContribution() throws Exception {
        File file = new File("C:\\Users\\nqxxz\\Desktop\\MidExam19.pdf");
        FileInputStream in_file = new FileInputStream(file);
        MultipartFile multi = new MockMultipartFile("MidExam19.pdf", "MidExam19.pdf", "application/pdf", in_file);
        String[] topic = {"science"};
        authorService.modifyArticleContribution(multi, "MidExam19",
                "[{\"name\":\"w\",\"email\":\"qdw@1.co\",\"company\":\"sda\",\"area\":\"asd\"}]",
                "summary", topic, "pc11", meetingRepository.findByFullName("meetingRequest").getId().toString(),
                articleRepository.getArticle("MidExam19", "summary", "pc11", meetingRepository.findByFullName("meetingRequest").getId().toString()).getId().toString(),
                "C:\\Users\\nqxxz\\Downloads\\test");
    }

    //测试author的投稿详情
    void testGetContribution() {
        assertThat(authorService.getContribution("pc11", meetingRepository.findByFullName("meetingRequest").getId().toString()), notNullValue());
    }

    //测试chair获取pc信息
    void testGetPcList(Meeting meeting) {
        assertThat(chairService.getPcList(meeting.getId().toString()), notNullValue());
    }

    //测试chair查看所有稿件
    void testGetAllArticles(Meeting meeting) {
        assertThat(chairService.getAllArticles(meeting.getId().toString()), notNullValue());
    }

    //测试分配稿件
    void testAllotArticle(Meeting meeting) {
//        assertThat(chairService.allotArticle(meeting.getId().toString(), "withTopic"), is("success"));
        assertThat(chairService.allotArticle(meeting.getId().toString(), "withAverage"), is("success"));
    }

    //测试提前结束投稿
    void testFinishContribution(Meeting meeting) {
        assertThat(chairService.finishContribution(meeting.getId().toString()), is("success"));
    }

    //测试pc获取分配到的稿件详情
    Map<String, Object> testGetAllotedArticle(Meeting meeting) {
        return pcMemberService.getAllotedArticle("pc22", meeting.getId().toString());
    }

    //测试pc提交分数
    void testSubmitScore(Article article) {
        System.out.println(article.getId().toString());
        ScoreRequest scoreRequest = new ScoreRequest("pc22", article.getId().toString(),
                "2", "comment", "very good");
        assertThat(pcMemberService.submitScore(scoreRequest), is("success"));
    }

    //测试发布评审结果
    void testPublishScores(Meeting meeting) {
//        try {
//            chairService.publishScores(meeting.getId().toString());
//            fail("Expect a ReviewNotFinishedException");
//        } catch (ReviewNotFinishedException e) {
//            assertThat(e.getMessage(), is("There are still articles unreviewed, please waiting until finishes."));
//        }
        assertThat(chairService.firstPublishScores(meeting.getId().toString()), is("success"));
    }
}

