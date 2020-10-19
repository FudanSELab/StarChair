package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ChangeApplicationStatusRequest;
import fudan.se.lab2.controller.request.MeetingRequest;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.Topic;
import fudan.se.lab2.domain.TopicPcMeetingArticle;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.repository.TopicPcMeetingArticleRepository;
import fudan.se.lab2.repository.TopicRepository;
import fudan.se.lab2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ConferenceApplicationService {
    private MeetingRepository meetingRepository;
    private UserRepository userRepository;
    private RepositoryService repositoryService;
    private TopicPcMeetingArticleRepository topicPcMeetingArticleRepository;
    private TopicRepository topicRepository;

    @Autowired
    public ConferenceApplicationService(MeetingRepository meetingRepository,
                                        UserRepository userRepository,
                                        TopicPcMeetingArticleRepository topicPcMeetingArticleRepository,
                                        TopicRepository topicRepository,
                                        RepositoryService repositoryService) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
        this.repositoryService = repositoryService;
        this.topicPcMeetingArticleRepository = topicPcMeetingArticleRepository;
        this.topicRepository = topicRepository;
    }

    /**
     * 会议申请实现
     *
     * @param request 会议申请基本信息
     * @return 新申请的会议
     */
    public Meeting applyConference(MeetingRequest request) {
        String shortenForm = request.getShortenForm();
        String fullName = request.getFullName();
        String time = request.getTime();
        String location = request.getLocation();
        String ddl = request.getDdl();
        String publishingTime = request.getPublishingTime();
        String applicant = request.getApplicant();
        String[] topics = request.getTopic();
        Meeting newMeeting = new Meeting(fullName, shortenForm, time, location, ddl,
                publishingTime, applicant, "false", "pass", new HashSet<>());
        meetingRepository.save(newMeeting);
        for (String s : topics) {
            Topic topic = new Topic(s);
            TopicPcMeetingArticle relation = new TopicPcMeetingArticle();
            topicRepository.save(topic);
            relation.setTopic(topic);
            relation.setMeeting(newMeeting);
            topicPcMeetingArticleRepository.save(relation);
        }
        return newMeeting;
    }

    /**
     * 管理员页面返回待审核会议
     *
     * @return 待审核会议列表
     */
    public List<Object> getUncheckedConference() {
        List<Meeting> uncheckedMeeting = meetingRepository.findByIsVarified("false");
        return repositoryService.getMeetingAndTopicList(uncheckedMeeting);
    }

    /**
     * 通过ID找到具体要操作的会议
     * 修改会议isVarified状态，表示审核通过或不通过
     * 如果审核通过，申请人变为该会议chair
     * 返回新的待审核会议的list
     *
     * @param request 会议ID和审核是否通过status
     */
    public List<Meeting> changeApplicationStatus(ChangeApplicationStatusRequest request) {
        String applicationId = request.getApplicationId();
        String applicationStatus = request.getApplyStatus();
        Meeting meeting = repositoryService.findByMeetingId(applicationId); // 找到该会议
        meeting.setIsVarified(applicationStatus); // 修改会议审核状态
        if (applicationStatus.equals("pass")) { // 申请通过
            String applicant = meeting.getApplicant();
            User user = userRepository.findByUsername(applicant); // 找到申请人
            repositoryService.addUserAndMeetingAuthorities(user, meeting, "ROLE_CHAIR");
            repositoryService.addUserAndMeetingAuthorities(user, meeting, "ROLE_PCMEMBER");
            //添加topic的关联项
            List<TopicPcMeetingArticle> allRelation = topicPcMeetingArticleRepository.findByMeetingId(meeting.getId());
            for (TopicPcMeetingArticle r : allRelation) {
                r.setPcmember(user);
                topicPcMeetingArticleRepository.save(r);
            }
        }
        meetingRepository.save(meeting);
        return meetingRepository.findByIsVarified("false"); // 返回新的待审核会议list
    }
}
