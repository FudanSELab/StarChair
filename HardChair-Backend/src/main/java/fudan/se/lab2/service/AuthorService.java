package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.RebuttalRequest;
import fudan.se.lab2.domain.Article;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.Score;
import fudan.se.lab2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class AuthorService {
    private ArticleRepository articleRepository;
    private RepositoryService repositoryService;
    private AuthorRepository authorRepository;
    private TopicRepository topicRepository;
    private ScoreRepository scoreRepository;
    private AllMeetingsService allMeetingsService;

    @Autowired
    public AuthorService(ArticleRepository articleRepository,
                         RepositoryService repositoryService,
                         AuthorRepository authorRepository,
                         TopicRepository topicRepository,
                         ScoreRepository scoreRepository,
                         AllMeetingsService allMeetingsService) {
        this.articleRepository = articleRepository;
        this.repositoryService = repositoryService;
        this.authorRepository = authorRepository;
        this.topicRepository = topicRepository;
        this.allMeetingsService = allMeetingsService;
        this.scoreRepository = scoreRepository;
    }

    /**
     * 获取当前用户在当前会议的投稿详情
     *
     * @param username  用户名
     * @param meetingId 会议Id
     * @return 投稿详情list
     */
    public Map<String, Object> getContribution(String username, String meetingId) {
        Map<String, Object> map = new HashMap<>();
        List<Article> articleList = articleRepository.findByContributorAndMeetingId(username, meetingId);
        Meeting meeting = repositoryService.findByMeetingId(meetingId);
        if (meeting.getSubmitStatus().equals("insubmit") ||
                meeting.getSubmitStatus().equals("withTopic") ||
                meeting.getSubmitStatus().equals("withBurden") ||
                meeting.getSubmitStatus().equals("firstDiscussion")) { //如果还没有发布评审结果，score属性为null
            for (Article article : articleList) {
                article.setScores(null);
            }
        } else if (meeting.getSubmitStatus().equals("rebuttal")) {
            for (Article article : articleList) {
                if (article.getReviewStatus().equals("Rebuttaled")) {
                    article.setScores(substituteWithOldScore(article));
                }
            }
        }

        map.put("articleDetails", articleList);
        map.put("topicDetails", repositoryService.getTopic(meetingId));
        return map;
    }

    private Set<Score> substituteWithOldScore(Article article) {
        List<Score> scoreList = new ArrayList<>(article.getScores());
        ListIterator<Score> scoreListIterator = scoreList.listIterator();
        while (scoreListIterator.hasNext()) {
            Score score = scoreListIterator.next();
            if (score.getModifyStatus() != null && score.getModifyStatus().equals("modified")) {
                Score oldScore = scoreRepository.findScoreByOldId(score.getId().toString());
                scoreListIterator.remove();
                scoreListIterator.add(oldScore);
            }
        }
        return new HashSet<>(scoreList);
    }

    /**
     * 删除原投稿，修改稿件
     *
     * @return 是否成功信息
     */
    public String modifyArticleContribution(MultipartFile file, String articleName,
                                            String author, String summary, String[] topics,
                                            String username, String meetingId, String articleId, String parentDir) {
        Article oldArticle = articleRepository.findByArticleId(Long.parseLong(articleId));
        authorRepository.deleteArticleAuthor(oldArticle.getId());
        topicRepository.deleteArticleTopic(oldArticle.getId());
        articleRepository.deleteArticle(oldArticle.getId());
        return allMeetingsService.storeArticleContribution(file, articleName, author,
                summary, topics, username, meetingId, parentDir);
    }

    /**
     * 作者rebuttal，修改article的reviewStatus属性，存reason,开始二轮讨论
     *
     * @param rebuttalRequest reason,username,meetingId,articleId
     * @return success
     */
    public String authorRebuttal(RebuttalRequest rebuttalRequest) {
        String isRebuttaled = rebuttalRequest.getIsRebuttaled();
        Article article = articleRepository.findByArticleId(Long.parseLong(rebuttalRequest.getArticleId()));
        if (isRebuttaled.equals("false")) {
            article.setReviewStatus("Confirmed");
            articleRepository.save(article);
        } else if (isRebuttaled.equals("true")) {
            article.setReviewStatus("Rebuttaled");
            article.setRebuttal(rebuttalRequest.getReason());
            Set<Score> scoreSet = article.getScores();
            for (Score score : scoreSet) {
                score.setModifyStatus(null);
            }
            article.setScores(scoreSet);
            articleRepository.save(article);
        }
        return "success";
    }
}
