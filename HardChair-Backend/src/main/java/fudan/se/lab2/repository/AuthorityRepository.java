package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Authority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LBW
 */
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    List<Authority> findByAuthority(String authority);

    @Query(value = "from Authority where authority = ?1 and meeting_id = ?2")
    List<Authority> findByAuthorityAndMeetingId(String authority, Long meeting_id);

    @Query(value = "from Authority where authority = ?1 and meeting_id = ?2")
    Authority findByChairAndMeetingId(String authority, Long meeting_id);

    @Query(value = "from Authority where user_id = ?1 and meeting_id = ?2")
    List<Authority> findByUserIdAndMeetingId(Long user_id, Long meeting_id);

    @Query(value = "from Authority where authority = ?1 and user_id = ?2 and meeting_id = ?3")
    Authority findByAuthorAndUserIdAndMeetingId(String authority, Long user_id, Long meeting_id);
}
