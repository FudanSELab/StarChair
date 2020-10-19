package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    Article findByFilePath(String filePath);

    @Query(value = "from Article where contributor = ?1 and meetingId = ?2")
    List<Article> findByContributorAndMeetingId(String contributor, String meetingId);

    @Query(value = "from Article where meetingId = ?1")
    List<Article> findByMeetingId(String meetingId);

    @Query(value = "from Article where articleName = ?1 and summary = ?2 and contributor = ?3 and meetingId = ?4")
    Article getArticle(String articleName, String summary, String contributor, String meetingId);

    @Query(value = "from Article where id = ?1")
    Article findByArticleId(Long id);

    @Transactional
    @Modifying
    @Query(value = "delete from Article where ID = ?1", nativeQuery = true)
    void deleteArticle(Long article_id);

    @Transactional
    @Modifying
    @Query(value = "delete from ARTICLE_PC where ARTICLE_ID = ?1", nativeQuery = true)
    void deleteArticlePc(Long article_id);

    @Query(value = "SELECT a.* FROM ARTICLE a,ARTICLE_PC p WHERE p.PC_ID = ?1 AND a.ID = p.ARTICLE_ID AND a.MEETING_ID = ?2", nativeQuery = true)
    List<Article> getAllotedArticle(Long pc_id, String meetingId);
}
