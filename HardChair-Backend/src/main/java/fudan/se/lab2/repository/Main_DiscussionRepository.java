package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Main_Discussion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Main_DiscussionRepository extends CrudRepository<Main_Discussion, Long> {
    @Query(value = "SELECT * FROM Main_Discussion WHERE ARTICLE_ID = ?1 ORDER BY CREATE_TIME", nativeQuery = true)
    List<Main_Discussion> findMainByArticleId(String articleId);

    @Query(value = "SELECT * FROM Main_Discussion WHERE USERNAME = ?1 AND ARTICLE_ID = ?2 AND DISCUSS_STATUS = ?3", nativeQuery = true)
    List<Main_Discussion> findMainByUsernameAndArticleIdAndDiscussStatus(String username, String articleId, String discussStatus);
}
