package fudan.se.lab2.repository;

import fudan.se.lab2.domain.TopicPcMeetingArticle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicPcMeetingArticleRepository extends CrudRepository<TopicPcMeetingArticle, Long> {
    @Query(value = "from TopicPcMeetingArticle where meeting_id = ?1")
    List<TopicPcMeetingArticle> findByMeetingId(Long meeting_id);

    @Query(value = "from TopicPcMeetingArticle where topic_id = ?1")
    List<TopicPcMeetingArticle> findByTopic_id(Long topic_id);

    @Query(value = "from TopicPcMeetingArticle where user_id = ?1")
    List<TopicPcMeetingArticle> findByUser_id(Long user_id);

    @Query(value = "from TopicPcMeetingArticle where article_id = ?1")
    List<TopicPcMeetingArticle> findByArticle_id(Long article_id);

    @Query(value = "SELECT * from TOPIC_PC_MEETING_ARTICLE where MEETING_ID = ?1 and TOPIC_ID = ?2", nativeQuery = true)
    List<TopicPcMeetingArticle> findByMeetingAndTopic(Long meeting_id, Long topic_id);

    @Query(value = "SELECT r.* from Topic t,TOPIC_PC_MEETING_ARTICLE r where r.MEETING_ID = ?1 and t.TOPIC = ?2 and r.TOPIC_ID = t.ID", nativeQuery = true)
    List<TopicPcMeetingArticle> findByMeetingAndTopic(Long meeting_id, String topic);

    @Query(value = "SELECT * from TOPIC_PC_MEETING_ARTICLE where MEETING_ID = ?1 and TOPIC_ID = ?2 and USER_ID = ?3", nativeQuery = true)
    TopicPcMeetingArticle findByMeetingAndTopicAndPc(Long meeting_id, Long topic_id, Long pc_id);

    @Query(value = "SELECT r.* from USER u,TOPIC_PC_MEETING_ARTICLE r where r.MEETING_ID = ?1 and u.ID = ?2 and r.USER_ID = u.ID", nativeQuery = true)
    List<TopicPcMeetingArticle> findByMeetingAndPc(Long meeting_id, Long user_id);
}
