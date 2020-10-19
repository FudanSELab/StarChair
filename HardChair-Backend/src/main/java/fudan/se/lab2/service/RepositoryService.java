package fudan.se.lab2.service;

import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.InvitationNotFoundException;
import fudan.se.lab2.exception.MeetingNotFoundException;
import fudan.se.lab2.exception.UserNotFoundException;
import fudan.se.lab2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RepositoryService {
    private UserRepository userRepository;
    private MeetingRepository meetingRepository;
    private AuthorityRepository authorityRepository;
    private InvitationRepository invitationRepository;
    private TopicPcMeetingArticleRepository topicPcMeetingArticleRepository;
    private TopicRepository topicRepository;
    private AuthorRepository authorRepository;
    private ScoreRepository scoreRepository;
    private Sub_DiscussionRepository subDiscussionRepository;

    @Autowired
    public RepositoryService(UserRepository userRepository,
                             MeetingRepository meetingRepository,
                             AuthorityRepository authorityRepository,
                             TopicPcMeetingArticleRepository topicPcMeetingArticleRepository,
                             TopicRepository topicRepository,
                             AuthorRepository authorRepository,
                             ScoreRepository scoreRepository,
                             InvitationRepository invitationRepository,
                             Sub_DiscussionRepository subDiscussionRepository) {
        this.userRepository = userRepository;
        this.meetingRepository = meetingRepository;
        this.authorityRepository = authorityRepository;
        this.invitationRepository = invitationRepository;
        this.topicPcMeetingArticleRepository = topicPcMeetingArticleRepository;
        this.topicRepository = topicRepository;
        this.authorRepository = authorRepository;
        this.scoreRepository = scoreRepository;
        this.subDiscussionRepository = subDiscussionRepository;
    }

    /**
     * 通过字符串形式的会议ID找到对应的会议，若找不到则抛出异常
     *
     * @param meetingId 字符串形式的会议Id
     * @return 会议Id对应的会议
     */
    Meeting findByMeetingId(String meetingId) {
        Optional<Meeting> meetingOptional = meetingRepository.findById(Long.parseLong(meetingId));
        if (meetingOptional.isPresent()) {
            return meetingOptional.get();
        } else {
            throw new MeetingNotFoundException();
        }
    }

    /**
     * 为指定user和meeting新增用户角色authority
     *
     * @param user      用户
     * @param authority 新增角色
     */
    void addUserAndMeetingAuthorities(User user, Meeting meeting, String authority) {
        Authority newAuthority = new Authority(authority);
        //修改user的authoritySet
        Set<Authority> userAuthority = new HashSet<>();
        if (user.getAuthorities() != null) {
            userAuthority = user.getAuthorities();
        }
        userAuthority.add(newAuthority);
        user.setAuthorities(userAuthority);
        //修改meeting的authoritySet
        Set<Authority> meetingAuthority = new HashSet<>();
        if (meeting.getAuthorities() != null) {
            meetingAuthority = meeting.getAuthorities();
        }
        meetingAuthority.add(newAuthority);
        meeting.setAuthorities(meetingAuthority);
        //存入数据库
        newAuthority.setUser(user);
        newAuthority.setMeeting(meeting);
        authorityRepository.save(newAuthority);
    }

    /**
     * 通过字符串形式的邀请ID找到对应的邀请，若找不到则抛出异常
     *
     * @param invitationId 字符串形式的邀请Id
     * @return 邀请Id对应的邀请
     */
    Invitation findByInvitationId(String invitationId) {
        Optional<Invitation> invitationOptional = invitationRepository.findById(Long.parseLong(invitationId));
        if (invitationOptional.isPresent()) {
            return invitationOptional.get();
        } else {
            throw new InvitationNotFoundException();
        }
    }

    /**
     * 通过字符串形式的userID找到对应的user，若找不到则抛出异常
     *
     * @param userId 字符串形式的userId
     * @return 邀请Id对应的user
     */
    User findByUserId(String userId) {
        Optional<User> userOptional = userRepository.findById(Long.parseLong(userId));
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    /**
     * 遍历传入的meeting list，查找对应的topic list，将两个list按顺序合并为一个list返回
     *
     * @param meetings 传入的meeting list
     * @return 新的带topic的list
     */
    List<Object> getMeetingAndTopicList(List<Meeting> meetings) {
        List<Object> newList = new ArrayList<>();
        for (Meeting meeting : meetings) {
            List<Topic> topics = new ArrayList<>();
            newList.add(meeting);
            List<TopicPcMeetingArticle> relation = topicPcMeetingArticleRepository.findByMeetingId(meeting.getId());
            for (TopicPcMeetingArticle rel : relation) {
                if (!topics.contains(rel.getTopic()))
                    topics.add(rel.getTopic());
            }
            newList.add(topics);
        }
        return newList;
    }

    /**
     * 获取某个指定会议的topic list
     *
     * @param meetingId 会议id
     * @return 该会议的topic
     */
    List<Topic> getTopic(String meetingId) {
        Meeting meeting = findByMeetingId(meetingId);
        List<Topic> topics = new ArrayList<>();
        List<TopicPcMeetingArticle> relation = topicPcMeetingArticleRepository.findByMeetingId(meeting.getId());
        for (TopicPcMeetingArticle rel : relation) {
            if (!topics.contains(rel.getTopic()))
                topics.add(rel.getTopic());
        }
        return topics;
    }

    /**
     * 找到对应会议list的topic list并返回
     *
     * @param meetings meeting list
     * @return topic list
     */
    List<Object> getTopicList(List<Meeting> meetings) {
        List<Object> newList = new ArrayList<>();
        for (Meeting meeting : meetings) {
            List<Topic> topics = new ArrayList<>();
            List<TopicPcMeetingArticle> relation = topicPcMeetingArticleRepository.findByMeetingId(meeting.getId());
            for (TopicPcMeetingArticle rel : relation) {
                if (!topics.contains(rel.getTopic()))
                    topics.add(rel.getTopic());
            }
            newList.add(topics);
        }
        return newList;
    }

    /**
     * 设置article与pc的对应关系
     *
     * @param article article
     * @param pc      对应的pc
     */
    void allotArticleToPc(Article article, User pc) {
        Set<Article> tempArticle = new HashSet<>();
        Set<User> tempPc = new HashSet<>();
        Set<Score> tempScore = new HashSet<>();
        if (pc.getArticles() != null) {
            tempArticle = pc.getArticles();
        }
        tempArticle.add(article);
        pc.setArticles(tempArticle);
        if (article.getPcmembers() != null) {
            tempPc = article.getPcmembers();
        }
        tempPc.add(pc);
        Score score = new Score(pc.getId());
        if (article.getScores() != null) {
            tempScore = article.getScores();
        }
        tempScore.add(score);
        article.setScores(tempScore);
        article.setPcmembers(tempPc);
        article.setReviewStatus("Reviewing");
        score.setArticle2(article);
        scoreRepository.save(score);
    }

    /**
     * 设置article的topics
     *
     * @param article article
     * @param topics  对应的topic
     * @return article
     */
    Article setArticleTopic(Article article, String[] topics) {
        for (String s : topics) {
            Topic topic = new Topic(s);
            article.getTopics().add(topic);
            topic.setArticle(article);
            topicRepository.save(topic);
        }
        return article;
    }

    Article setArticleAuthor(Article article, List<Author> authorList) {
        for (int i = 0; i < authorList.size(); i++) {
            Author newAuthor = authorList.get(i);
            newAuthor.setOrderOfAuthor(i);
            article.getAuthors().add(newAuthor);
            newAuthor.setArticle1(article);
            authorRepository.save(newAuthor);
        }
        return article;
    }

    /**
     * 为主题贴新增回复或回复的回复
     *
     * @param mainDiscussion 主题贴
     * @param subDiscussion  回复
     */
    void addNewComment(Main_Discussion mainDiscussion, Sub_Discussion subDiscussion) {
        List<Sub_Discussion> subDiscussions = new ArrayList<>();
        if (mainDiscussion.getSub_discussions() != null) {
            subDiscussions = mainDiscussion.getSub_discussions();
        }
        subDiscussions.add(subDiscussion);
        mainDiscussion.setSub_discussions(subDiscussions);
        subDiscussion.setMain_discussion(mainDiscussion);
        subDiscussionRepository.save(subDiscussion);
    }
}
