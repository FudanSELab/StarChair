package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.NewCommentRequest;
import fudan.se.lab2.controller.request.NewPostRequest;
import fudan.se.lab2.controller.request.ReplyCommentRequest;
import fudan.se.lab2.domain.Article;
import fudan.se.lab2.domain.Main_Discussion;
import fudan.se.lab2.domain.Sub_Discussion;
import fudan.se.lab2.repository.ArticleRepository;
import fudan.se.lab2.repository.Main_DiscussionRepository;
import fudan.se.lab2.repository.Sub_DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService {
    private Main_DiscussionRepository mainDiscussionRepository;
    private Sub_DiscussionRepository subDiscussionRepository;
    private RepositoryService repositoryService;
    private ArticleRepository articleRepository;

    @Autowired
    public DiscussionService(Main_DiscussionRepository mainDiscussionRepository,
                             Sub_DiscussionRepository subDiscussionRepository,
                             RepositoryService repositoryService,
                             ArticleRepository articleRepository) {
        this.mainDiscussionRepository = mainDiscussionRepository;
        this.subDiscussionRepository = subDiscussionRepository;
        this.repositoryService = repositoryService;
        this.articleRepository = articleRepository;
    }

    /**
     * 获取指定article的讨论页面数据
     *
     * @return 主贴的列表，每个主贴里包括一个回复的列表
     */
    public List<Main_Discussion> getArticleDiscussion(String articleId) {
        return mainDiscussionRepository.findMainByArticleId(articleId);
    }

    /**
     * 存储回贴信息
     *
     * @return 返回新的贴子数据
     */
    public List<Main_Discussion> sendNewPost(NewPostRequest newPostRequest) {
        String articleId = newPostRequest.getArticleId();
        Article article = articleRepository.findByArticleId(Long.parseLong(articleId));
        String discussStatus = "";
        if (article.getReviewStatus().equals("Reviewed")) {
            discussStatus = "firstDiscussion";
        } else if (article.getReviewStatus().equals("Rebuttaled")) {
            discussStatus = "secondDiscussion";
        }
        Main_Discussion mainDiscussion = new Main_Discussion(articleId, newPostRequest.getUsername(), newPostRequest.getContent(), discussStatus);
        mainDiscussionRepository.save(mainDiscussion);
        return getArticleDiscussion(articleId);
    }

    /**
     * 回复回贴
     *
     * @param newCommentRequest 回复内容
     * @return 返回新的贴子数据
     */
    public List<Main_Discussion> sendNewComment(NewCommentRequest newCommentRequest) {
        String articleId = newCommentRequest.getArticleId();
        Article article = articleRepository.findByArticleId(Long.parseLong(articleId));
        String discussStatus = "";
        if (article.getReviewStatus().equals("Reviewed")) {
            discussStatus = "firstDiscussion";
        } else if (article.getReviewStatus().equals("Rebuttaled")) {
            discussStatus = "secondDiscussion";
        }
        Sub_Discussion subDiscussion = new Sub_Discussion(articleId, newCommentRequest.getContent(),
                newCommentRequest.getUsername(), discussStatus);
        Optional<Main_Discussion> optional = mainDiscussionRepository.findById(Long.parseLong(newCommentRequest.getMainId()));
        if (optional.isPresent()) {
            Main_Discussion mainDiscussion = optional.get();
            repositoryService.addNewComment(mainDiscussion, subDiscussion);
        }
        return getArticleDiscussion(articleId);
    }

    /**
     * 回复回复
     *
     * @param replyCommentRequest 回复信息
     * @return 返回新的贴子数据
     */
    public List<Main_Discussion> replyComment(ReplyCommentRequest replyCommentRequest) {
        String articleId = replyCommentRequest.getArticleId();
        Sub_Discussion fatherComment = subDiscussionRepository.findSubBySubId(replyCommentRequest.getSubId());
        Main_Discussion mainDiscussion = fatherComment.getMain_discussion();
        Article article = articleRepository.findByArticleId(Long.parseLong(articleId));
        String discussStatus = "";
        if (article.getReviewStatus().equals("Reviewed")) {
            discussStatus = "firstDiscussion";
        } else if (article.getReviewStatus().equals("Rebuttaled")) {
            discussStatus = "secondDiscussion";
        }
        Sub_Discussion subDiscussion = new Sub_Discussion(articleId, replyCommentRequest.getContent(),
                replyCommentRequest.getUsername(), fatherComment.getSubId().toString(), fatherComment.getUsername(), discussStatus);
        repositoryService.addNewComment(mainDiscussion, subDiscussion);
        return getArticleDiscussion(articleId);
    }
}
