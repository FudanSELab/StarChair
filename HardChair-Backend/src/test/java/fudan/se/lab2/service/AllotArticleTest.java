package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.Article;
import fudan.se.lab2.domain.Main_Discussion;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.repository.ArticleRepository;
import fudan.se.lab2.repository.MeetingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AllotArticleTest {
    @Autowired
    private ChairService chairService;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private PcMemberService pcMemberService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private DiscussionService discussionService;
    @Autowired
    private AuthorService authorService;

    @Test
    @Transactional
    public void testAllotArticle() {
        Meeting meeting = meetingRepository.findByFullName("conferenceTest1");

        //1. 测试分配稿件
        assertThat(chairService.allotArticle(meeting.getId().toString(), "withTopic"), is("success"));

        //2. 测试pc获取分配到的稿件详情
//        assertThat(pcMemberService.getAllotedArticle("pc2", meeting.getId().toString()), notNullValue());
//        assertThat(pcMemberService.getAllotedArticle("pc3", meeting.getId().toString()), notNullValue());
//        assertThat(pcMemberService.getAllotedArticle("registerTest", meeting.getId().toString()), notNullValue());

        //3. 测试pc提交分数
        Article article = articleRepository.findByMeetingId(meeting.getId().toString()).get(0);
        ScoreRequest scoreRequest1 = new ScoreRequest("registerTest", article.getId().toString(),
                "2", "comment", "very good");
        ScoreRequest scoreRequest2 = new ScoreRequest("pc2", article.getId().toString(),
                "2", "comment", "very good");
        ScoreRequest scoreRequest3 = new ScoreRequest("pc3", article.getId().toString(),
                "2", "comment", "very good");
        pcMemberService.submitScore(scoreRequest1);
        pcMemberService.submitScore(scoreRequest2);
        pcMemberService.submitScore(scoreRequest3);

        //4. 测试chair查看所有稿件
        assertThat(chairService.getAllArticles(meeting.getId().toString()), notNullValue());

        //5. 测试结束一轮评审
        assertThat(chairService.finishReview(meeting.getId().toString()), is("success"));

        //6. 测试查看讨论
        assertThat(discussionService.getArticleDiscussion(article.getId().toString()), notNullValue());

        //7. 测试一轮讨论
        NewPostRequest newPostRequest = new NewPostRequest(article.getId().toString(), "pc2", "new post");
        List<Main_Discussion> mainDiscussions = discussionService.sendNewPost(newPostRequest);
        NewCommentRequest newCommentRequest = new NewCommentRequest(article.getId().toString(),
                mainDiscussions.get(1).getMainId().toString(), "pc3", "new comment");
        mainDiscussions = discussionService.sendNewComment(newCommentRequest);
        ReplyCommentRequest replyCommentRequest = new ReplyCommentRequest(article.getId().toString(),
                mainDiscussions.get(1).getSub_discussions().get(0).getSubId().toString(), "registerTest", "reply comment");
        assertThat(discussionService.replyComment(replyCommentRequest), notNullValue());

        //8. 测试pc修改分数
        ModifyScoreRequest modifyScoreRequest1 = new ModifyScoreRequest("pc2", article.getId().toString(),
                "-2", "comment", "very good", "modified");
        ModifyScoreRequest modifyScoreRequest2 = new ModifyScoreRequest("pc3", article.getId().toString(),
                "1", "comment", "very good", "modified");
        ModifyScoreRequest modifyScoreRequest3 = new ModifyScoreRequest("registerTest", article.getId().toString(),
                "1", "comment", "very good", "confirmed");
        assertThat(pcMemberService.submitModify(modifyScoreRequest1), notNullValue());
        assertThat(pcMemberService.submitModify(modifyScoreRequest2), notNullValue());
        assertThat(pcMemberService.submitModify(modifyScoreRequest3), notNullValue());

        //9. 测试测试发布一轮结果
        assertThat(chairService.firstPublishScores(meeting.getId().toString()), is("success"));

        //10. 测试author Rebuttal
        RebuttalRequest rebuttalRequest = new RebuttalRequest("reason", "pc1", meeting.getId().toString(), article.getId().toString(), "true");
        assertThat(authorService.authorRebuttal(rebuttalRequest), is("success"));

        //11. 测试二轮讨论
        NewPostRequest newPostRequest2 = new NewPostRequest(article.getId().toString(), "pc2", "new post");
        mainDiscussions = discussionService.sendNewPost(newPostRequest2);
        NewCommentRequest newCommentRequest2 = new NewCommentRequest(article.getId().toString(),
                mainDiscussions.get(1).getMainId().toString(), "pc3", "new comment");
        mainDiscussions = discussionService.sendNewComment(newCommentRequest2);
        ReplyCommentRequest replyCommentRequest2 = new ReplyCommentRequest(article.getId().toString(),
                mainDiscussions.get(1).getSub_discussions().get(0).getSubId().toString(), "registerTest", "reply comment");
        assertThat(discussionService.replyComment(replyCommentRequest2), notNullValue());
        assertThat(authorService.getContribution("pc1", meeting.getId().toString()), notNullValue());

        //12. 测试pc二轮修改分数
        ModifyScoreRequest modifyScoreRequest11 = new ModifyScoreRequest("pc2", article.getId().toString(),
                "1", "comment", "very good", "modified");
        ModifyScoreRequest modifyScoreRequest22 = new ModifyScoreRequest("pc3", article.getId().toString(),
                "1", "comment", "very good", "confirmed");
        ModifyScoreRequest modifyScoreRequest33 = new ModifyScoreRequest("registerTest", article.getId().toString(),
                "1", "comment", "very good", "confirmed");
        assertThat(pcMemberService.submitModify(modifyScoreRequest11), notNullValue());
        assertThat(pcMemberService.submitModify(modifyScoreRequest22), notNullValue());
        assertThat(pcMemberService.submitModify(modifyScoreRequest33), notNullValue());

        //13. 测试发布最终结果
        assertThat(chairService.finalPublishScores(meeting.getId().toString()), is("success"));
    }
}
