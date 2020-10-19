package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Sub_Discussion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sub_DiscussionRepository extends CrudRepository<Sub_Discussion, Long> {
    @Query(value = "SELECT * FROM Sub_Discussion WHERE SUB_ID = ?1", nativeQuery = true)
    Sub_Discussion findSubBySubId(String subId);

    @Query(value = "SELECT * FROM Sub_Discussion WHERE USERNAME = ?1 AND ARTICLE_ID = ?2 AND DISCUSS_STATUS = ?3", nativeQuery = true)
    List<Sub_Discussion> findSubByUsernameAndArticleIdAndDiscussStatus(String username, String articleId, String discussStatus);
}
