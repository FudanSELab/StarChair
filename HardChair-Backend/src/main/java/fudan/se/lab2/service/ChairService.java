package fudan.se.lab2.service;

import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.AllocationException;
import fudan.se.lab2.exception.ReviewNotFinishedException;
import fudan.se.lab2.exception.ScoreNotConfirmedException;
import fudan.se.lab2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChairService {
    private MeetingRepository meetingRepository;
    private UserRepository userRepository;
    private InvitationRepository invitationRepository;
    private RepositoryService repositoryService;
    private AuthorityRepository authorityRepository;
    private TopicPcMeetingArticleRepository topicPcMeetingArticleRepository;
    private ArticleRepository articleRepository;
    private Main_DiscussionRepository mainDiscussionRepository;
    private Random rand = new Random();

    @Autowired
    public ChairService(MeetingRepository meetingRepository,
                        UserRepository userRepository,
                        InvitationRepository invitationRepository,
                        RepositoryService repositoryService,
                        TopicPcMeetingArticleRepository topicPcMeetingArticleRepository,
                        ArticleRepository articleRepository,
                        AuthorityRepository authorityRepository,
                        Main_DiscussionRepository mainDiscussionRepository) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.repositoryService = repositoryService;
        this.authorityRepository = authorityRepository;
        this.topicPcMeetingArticleRepository = topicPcMeetingArticleRepository;
        this.articleRepository = articleRepository;
        this.mainDiscussionRepository = mainDiscussionRepository;
    }

    /**
     * 用用户的真实姓名查找所有用户
     *
     * @param fullName 用户的真实姓名
     * @return 用户列表
     */
    public List<User> searchForUser(String fullName) {
        return userRepository.findByFullName(fullName);
    }

    /**
     * 发送邀请到被邀请用户,相同邀请只存一次
     *
     * @param meetingID 会议ID
     * @param inviter   邀请人
     * @param invitee   被邀请人ID数组
     * @return message 是否成功
     */
    public String sendInvitation(String meetingID, String inviter, String[] invitee) {
        for (String invited : invitee) {
            User user = userRepository.findByUsername(inviter);
            Optional<Invitation> optionalInvitation = invitationRepository.findByInviterAndInviteeAndMeetingID(inviter, invited, meetingID);
            if (!optionalInvitation.isPresent() && !user.getId().toString().equals(invited)) {
                Invitation invitation = new Invitation(inviter, invited, meetingID, "false");
                invitationRepository.save(invitation);
            }
        }
        return "success";
    }

    /**
     * 修改会议的投稿状态
     *
     * @param id           会议id
     * @param submitStatus 变量记录是否开启投稿
     * @return 是否修改成功message
     */
    public String changeSubmitStatus(String id, String submitStatus) {
        Meeting meeting = repositoryService.findByMeetingId(id);
        meeting.setSubmitStatus(submitStatus);
        meetingRepository.save(meeting);
        return "success";
    }

    /**
     * 查找指定会议的pcmember
     *
     * @param meetingID 指定会议ID
     * @return pcmember列表及其对应的topic列表
     */
    public Map<String, Object> getPcList(String meetingID) {
        Long meetingId = Long.parseLong(meetingID);
        Meeting meeting = repositoryService.findByMeetingId(meetingID);
        List<Authority> authorityList = authorityRepository.findByAuthorityAndMeetingId("ROLE_PCMEMBER", meetingId);
        List<User> userList = new ArrayList<>();
        for (Authority authority : authorityList) {
            userList.add(authority.getUser());
        }
        //找到对应pc的topic
        List<Object> newList = new ArrayList<>(); //topic list
        for (User pc : userList) {
            List<Topic> topics = new ArrayList<>();
            List<TopicPcMeetingArticle> relation = topicPcMeetingArticleRepository.findByMeetingAndPc(meetingId, pc.getId());
            for (TopicPcMeetingArticle rel : relation) {
                if (!topics.contains(rel.getTopic()))
                    topics.add(rel.getTopic());
            }
            newList.add(topics);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userDetails", userList);
        map.put("topicDetails", newList);
        map.put("submitStatus", meeting.getSubmitStatus());
        return map;
    }

    /**
     * 基于两种分配方式，分配article给pcmember
     *
     * @param meetingId 会议ID
     * @param strategy  分配策略
     */
    public String allotArticle(String meetingId, String strategy) {
        Meeting meeting = repositoryService.findByMeetingId(meetingId);
        List<Authority> pcAuthList = authorityRepository.findByAuthorityAndMeetingId("ROLE_PCMEMBER", meeting.getId());
        List<User> allPcList = new ArrayList<>(); //该会议所有pcmember
        for (Authority authority : pcAuthList) {
            allPcList.add(authority.getUser());
        }
        if (allPcList.size() < 3) { //pcmember不足3人，分配失败
            throw new AllocationException();
        }
        List<Article> articleList = articleRepository.findByMeetingId(meetingId); //该会议的所有article
        if (articleList.isEmpty()) {
            return "No article found!";
        }
        if (strategy.equals("withTopic")) { //基于topic分配
            allotWithTopic(meeting.getId(), articleList, allPcList);
        } else { //平均分配
            allotWithBurden(articleList, allPcList);
        }
        meeting.setSubmitStatus(strategy); //修改meeting的状态，从投稿改为审稿
        meetingRepository.save(meeting);
        return "success";
    }

    private void allotWithTopic(Long meetingId, List<Article> articleList, List<User> allPcList) {
        for (Article article : articleList) {
            User contributor = userRepository.findByUsername(article.getContributor()); //投稿人
            List<User> authorList = userRepository.findByAuthor(article.getId().toString());
            Set<Topic> topics = article.getTopics(); //该稿件的topic
            List<TopicPcMeetingArticle> relation = new ArrayList<>(); //该topic list对应的所有关系
            List<User> pcList = new ArrayList<>(); //该topic list对应的pcmember
            for (Topic topic : topics) {
                relation.addAll(topicPcMeetingArticleRepository.findByMeetingAndTopic(meetingId, topic.getId()));
            }
            for (TopicPcMeetingArticle r : relation) {
                if (!pcList.contains(r.getPcmember())) {
                    pcList.add(r.getPcmember());
                }
            }
            List<User> allPcListWithoutAuthor = new ArrayList<>(allPcList);
            allPcListWithoutAuthor.removeAll(authorList); //去除该稿件的author后的会议的所以pc
            allPcListWithoutAuthor.remove(contributor);
            if (allPcListWithoutAuthor.size() < 3) {
                for (Article a : articleList) { //清除之前的分配数据
                    a.setPcmembers(null);
                    articleRepository.save(a);
                }
                throw new AllocationException();
            } else {
                pcList.removeAll(authorList); //去除该稿件的author后的该稿件对应的pc
                pcList.remove(contributor);
                int numberOfPc = pcList.size(); //该topic对应的pcmember个数
                if (numberOfPc < 3) { //在会议的所有pcmember里随机分配
                    int count = 0;
                    while (count < 3) {
                        int num = rand.nextInt(allPcListWithoutAuthor.size());
                        User pc = allPcListWithoutAuthor.get(num);
                        if (!article.getPcmembers().contains(pc)) {
                            repositoryService.allotArticleToPc(article, pc);
                            count++;
                        }
                    }
                } else if (numberOfPc == 3) {
                    for (int i = 0; i < 3; i++) {
                        User pc = pcList.get(i);
                        repositoryService.allotArticleToPc(article, pc);
                    }
                } else {
                    int count = 0;
                    while (count < 3) {
                        int num = rand.nextInt(numberOfPc);
                        User pc = pcList.get(num);
                        if (!article.getPcmembers().contains(pc)) {
                            repositoryService.allotArticleToPc(article, pc);
                            count++;
                        }
                    }
                }
            }
        }
    }

    private void allotWithBurden(List<Article> articleList, List<User> allPcList) {
        HashMap pcMap = new HashMap(); //存储pc和其对应的article个数
        for (User pc : allPcList) {
            pcMap.put(pc, 0);
        }
        for (Article article : articleList) {
            User contributor = userRepository.findByUsername(article.getContributor()); //投稿人
            List<User> authorList = userRepository.findByAuthor(article.getId().toString());
            List<User> allPcListWithoutAuthor = new ArrayList<>(allPcList);
            allPcListWithoutAuthor.removeAll(authorList); //去除该稿件的author后的会议的所以pc
            allPcListWithoutAuthor.remove(contributor);
            if (allPcListWithoutAuthor.size() < 3) {
                for (Article a : articleList) { //清除之前的分配数据
                    a.setPcmembers(null);
                    articleRepository.save(a);
                }
                throw new AllocationException();
            } else {
                HashMap subMap = new HashMap();
                for (User a : allPcListWithoutAuthor) {
                    subMap.put(a, pcMap.get(a));
                }
                List<Map.Entry<User, Integer>> list = new ArrayList<Map.Entry<User, Integer>>(subMap.entrySet()); //转换为list
                list.sort(Comparator.comparing(Map.Entry::getValue));
                for (int i = 0; i < 3; i++) {
                    repositoryService.allotArticleToPc(article, list.get(i).getKey());
                    pcMap.put(list.get(i).getKey(), list.get(i).getValue() + 1);
                }
            }
        }
    }

    /**
     * 用户确认结束投稿
     *
     * @param meetingId 会议id
     * @return 是否成功
     */
    public String finishContribution(String meetingId) {
        Meeting meeting = repositoryService.findByMeetingId(meetingId);
        meeting.setSubmitStatus("Reviewing");
        meetingRepository.save(meeting);
        return "success";
    }

    /**
     * 查看该会议所有稿件
     *
     * @param meetingId 会议Id
     * @return article list
     */
    public List<Article> getAllArticles(String meetingId) {
        Meeting meeting = repositoryService.findByMeetingId(meetingId);
        List<Article> articleList = articleRepository.findByMeetingId(meetingId);
        if (meeting.getSubmitStatus().equals("withTopic") || meeting.getSubmitStatus().equals("withAverage")) {
            for (Article article : articleList) {
                Set<Score> scores = article.getScores();
                int count = 0;
                for (Score score : scores) {
                    if (score.getReviewStatus() != null) {
                        count++;
                    }
                }
                if (count == 3) {
                    article.setReviewStatus("Reviewed");
                    articleRepository.save(article);
                }
            }
        }
        return articleList;
    }

    /**
     * 结束评审，修改会议的submitStatus，如果评审还未结束，抛出异常
     *
     * @param meetingId 会议Id
     * @return 是否成功
     */
    public String finishReview(String meetingId) {
        List<Article> articleList = articleRepository.findByMeetingId(meetingId);
        boolean finished = true;
        for (Article article : articleList) {
            if (article.getReviewStatus() == null) {
                finished = false;
                break;
            }
        }
        if (!finished) {
            throw new ReviewNotFinishedException();
        }
        Meeting meeting = repositoryService.findByMeetingId(meetingId);
        meeting.setSubmitStatus("firstDiscussion");
        meetingRepository.save(meeting);
        for (Article article : articleList) { //建每个article的讨论贴第一条
            Main_Discussion mainDiscussion = new Main_Discussion(article.getId().toString(), meeting.getApplicant(), null);
            mainDiscussion.setContent("Feel free to share your precious opinions on this paper.");
            mainDiscussionRepository.save(mainDiscussion);
        }
        return "success";
    }

    /**
     * 发布初次评审结果，修改会议的submitStatus
     *
     * @param meetingId 会议Id
     * @return 是否成功
     */
    public String firstPublishScores(String meetingId) {
        List<Article> articleList = articleRepository.findByMeetingId(meetingId);
        for (Article article : articleList) { //判断分数是否都已确认
            Set<Score> scoreList = article.getScores();
            for (Score score : scoreList) {
                if (score.getModifyStatus() == null) {
                    throw new ScoreNotConfirmedException();
                }
            }
        }
        checkIfAccepted(articleList); //判断是否被录用
        Meeting meeting = repositoryService.findByMeetingId(meetingId);
        meeting.setSubmitStatus("rebuttal");
        meetingRepository.save(meeting);
        return "success";
    }

    /**
     * 发布最终评审结果，修改会议的submitStatus
     *
     * @param meetingId 会议Id
     * @return 是否成功
     */
    public String finalPublishScores(String meetingId) {
        List<Article> articleList = articleRepository.findByMeetingId(meetingId);
        for (Article article : articleList) { //判断分数是否都已确认
            Set<Score> scoreList = article.getScores();
            for (Score score : scoreList) {
                if (score.getModifyStatus() == null) {
                    throw new ScoreNotConfirmedException();
                }
            }
        }
        checkIfAccepted(articleList); //判断是否被录用
        Meeting meeting = repositoryService.findByMeetingId(meetingId);
        meeting.setSubmitStatus("final");
        meetingRepository.save(meeting);
        return "success";
    }

    /**
     * 判断article是否被录用，修改article的isAccepted属性，录用为true，否则为false
     *
     * @param articleList articleList
     */
    private void checkIfAccepted(List<Article> articleList) {
        for (Article article : articleList) {
            Set<Score> scores = article.getScores();
            String isAccepted = "true";
            for (Score score : scores) {
                if (score.getScore().equals("-1") || score.getScore().equals("-2")) {
                    isAccepted = "false";
                    break;
                }
            }
            article.setIsAccepted(isAccepted);
            articleRepository.save(article);
        }
    }
}
