package fudan.se.lab2.domain;

import fudan.se.lab2.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ArticleTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @Transactional
    void testArticle() {
        Article article = new Article("testArticle",
                "summary", "src/main/file/testArticle", "username", "2","false");
        articleRepository.save(article);
        Article myArticle = articleRepository.findByFilePath("src/main/file/testArticle");
        myArticle.setArticleName("articleName");
        assertThat(myArticle.getArticleName(), is("articleName"));
        myArticle.setContributor("username");
        assertThat(myArticle.getContributor(), is("username"));
        myArticle.setSummary("hi");
        assertThat(myArticle.getSummary(), is("hi"));
        myArticle.setFilePath("src/file/main/articleName");
        assertThat(myArticle.getFilePath(), is("src/file/main/articleName"));
        myArticle.setMeetingId("10");
        assertThat(myArticle.getMeetingId(), is("10"));
        Set<Topic> topics = new HashSet<>();
        myArticle.setTopics(topics);
        assertThat(myArticle.getTopics(),is(topics));
        Set<User> userSet = new HashSet<>();
        myArticle.setPcmembers(userSet);
        assertThat(myArticle.getPcmembers(),is(userSet));
        myArticle.setReviewStatus("reviewed");
        assertThat(myArticle.getReviewStatus(),is("reviewed"));
        Set<Author> authors = new HashSet<>();
        myArticle.setAuthors(authors);
        assertThat(myArticle.getAuthors(),is(authors));
        Set<Score> scores = new HashSet<>();
        myArticle.setScores(scores);
        assertThat(myArticle.getScores(),is(scores));
        myArticle.setIsAccepted("true");
        assertThat(myArticle.getIsAccepted(),is("true"));
        myArticle.setRebuttal("rebuttaled");
        assertThat(myArticle.getRebuttal(),is("rebuttaled"));
    }
}