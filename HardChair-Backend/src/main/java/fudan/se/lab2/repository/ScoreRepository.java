package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Score;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ScoreRepository extends CrudRepository<Score, Long> {
    @Query(value = "SELECT * from SCORE where PCMEMBER_ID = ?1 and ARTICLE_ID = ?2", nativeQuery = true)
    Score findScoreByPcAndArticleId(Long pcmember_id, Long article_id);

    @Query(value = "SELECT * from SCORE where PCMEMBER_ID = ?1 and ARTICLE_ID = ?2 and MODIFY_STATUS = ?3", nativeQuery = true)
    Score findScoreByPcAndArticleIdAndModifyStatus(Long pcmember_id, Long article_id, String modifyStatus);

    @Query(value = "SELECT * from SCORE where OLD_ID = ?1", nativeQuery = true)
    Score findScoreByOldId(String oldId);
}
