package fudan.se.lab2.service;

import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserCenterService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private MeetingRepository meetingRepository;
    private InvitationRepository invitationRepository;
    private TopicPcMeetingArticleRepository topicPcMeetingArticleRepository;
    private RepositoryService repositoryService;

    @Autowired
    public UserCenterService(UserRepository userRepository,
                             AuthorityRepository authorityRepository,
                             MeetingRepository meetingRepository,
                             InvitationRepository invitationRepository,
                             TopicPcMeetingArticleRepository topicPcMeetingArticleRepository,
                             RepositoryService repositoryService) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.meetingRepository = meetingRepository;
        this.invitationRepository = invitationRepository;
        this.topicPcMeetingArticleRepository = topicPcMeetingArticleRepository;
        this.repositoryService = repositoryService;
    }

    /**
     * 查找所有我申请的会议并返回
     *
     * @return 所有我申请的会议list
     */
    public List<Object> myMeetingApplication(String username) {
        List<Meeting> uncheckedMeeting = meetingRepository.findByApplicant(username);
        return repositoryService.getMeetingAndTopicList(uncheckedMeeting);
    }

    /**
     * 查找我参与的所有会议
     *
     * @param username 用户名
     * @return 我参与的所有会议
     */
    public List<Object> myMeeting(String username) {
        User user = userRepository.findByUsername(username);
        Set<Authority> authoritySet = user.getAuthorities(); //通过角色列表查找对应的会议
        List<Meeting> meetingList = new ArrayList<>(); //返回的会议列表
        if (authoritySet != null) {
            for (Authority authority : authoritySet) {
                if (!meetingList.contains(authority.getMeeting()))
                    meetingList.add(authority.getMeeting());
            }
        }
        return repositoryService.getMeetingAndTopicList(meetingList);
    }

    /**
     * 返回指定用户在指定会议中的角色列表
     *
     * @param id       会议id
     * @param username 用户名
     * @return 指定用户在指定会议中的角色列表
     */
    public List<Authority> getUser_MeetingAuthorityList(String id, String username) {
        User user = userRepository.findByUsername(username);
        return authorityRepository.findByUserIdAndMeetingId(user.getId(), Long.parseLong(id));
    }

    /**
     * 通过用户名查找用户收到的所有邀请
     * 遍历邀请列表，通过邀请人用户名查找邀请人信息，通过会议ID查找会议信息
     *
     * @param id 用户的id
     * @return Map 所有被邀请人信息,所有会议信息和邀请状态
     */
    public Map<String, Object> invitationIReceived(String id) {
        List<Invitation> invitationList = invitationRepository.findByInviteeAndInvitationStatus(id, "false");//返回的所有邀请信息
        List<Meeting> meetingList = new ArrayList<>(); //返回的所有会议信息
        List<User> userList = new ArrayList<>(); //返回的所有邀请人信息
        for (Invitation invitations : invitationList) {
            meetingList.add(repositoryService.findByMeetingId(invitations.getMeetingId()));
            userList.add(userRepository.findByUsername(invitations.getInviter()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("meetingDetails", meetingList);
        map.put("inviterDetails", userList);
        map.put("invitationDetails", invitationList);
        map.put("topicDetails", repositoryService.getTopicList(meetingList));
        return map;
    }

    /**
     * 通过邀请ID查找邀请，修改邀请的状态
     *
     * @param id               邀请ID
     * @param invitationStatus 邀请状态
     * @return 剩余待处理邀请
     */
    public Map<String, Object> changeInvitationStatus(String id, String invitationStatus, String[] topics) {
        Invitation invitation = repositoryService.findByInvitationId(id); //找到对应的邀请
        invitation.setInvitationStatus(invitationStatus); //更改邀请的状态
        if (invitationStatus.equals("pass")) { //通过邀请，被邀请人成为pcMember
            // 将受邀请人变为pcMember
            User user = repositoryService.findByUserId(invitation.getInvitee());
            Meeting meeting = repositoryService.findByMeetingId(invitation.getMeetingId());
            repositoryService.addUserAndMeetingAuthorities(user, meeting, "ROLE_PCMEMBER");
            //添加topic的关联项
            List<TopicPcMeetingArticle> allRelation = new ArrayList<>();
            for (String s : topics) {
                allRelation.addAll(topicPcMeetingArticleRepository.findByMeetingAndTopic(meeting.getId(), s));
            } //找到所有的关联项
            for (TopicPcMeetingArticle r : allRelation) {
                if (topicPcMeetingArticleRepository.findByMeetingAndTopicAndPc(r.getMeeting().getId(), r.getTopic().getId(), user.getId()) == null) {
                    TopicPcMeetingArticle newRelation = new TopicPcMeetingArticle(r.getTopic(), user, r.getMeeting());
                    topicPcMeetingArticleRepository.save(newRelation);
                }
            }
        }
        invitationRepository.save(invitation);
        return invitationIReceived(id);
    }

    /**
     * 通过用户名查找用户发送的所有邀请
     * 遍历邀请列表，通过被邀请人ID查找被邀请人信息，通过会议ID查找会议信息
     *
     * @param username 用户名
     * @return Map 所有被邀请人信息,所有会议信息和邀请状态
     */
    public Map<String, Object> invitationISent(String username) {
        List<Invitation> invitationList = invitationRepository.findByInviter(username); //返回的所有邀请信息
        List<Meeting> meetingList = new ArrayList<>(); //返回的所有会议信息
        List<User> userList = new ArrayList<>(); //返回的所有被邀请人
        for (Invitation invitations : invitationList) {
            meetingList.add(repositoryService.findByMeetingId(invitations.getMeetingId()));
            userList.add(repositoryService.findByUserId(invitations.getInvitee()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("meetingDetails", meetingList);
        map.put("inviteeDetails", userList);
        map.put("invitationDetails", invitationList);
        map.put("topicDetails", repositoryService.getTopicList(meetingList));
        return map;
    }
}
