package fudan.se.lab2.repository;

import fudan.se.lab2.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LBW
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "from User where fullName = ?1")
    List<User> findByFullName(String fullName);

    @Query(value = "from User where e_mail = ?1")
    User findByE_mail(String email);

    @Query(value = "SELECT u.* from USER u, AUTHOR a where a.ARTICLE_ID = ?1 and u.FULL_NAME = a.name and u.e_mail = a.email", nativeQuery = true)
    List<User> findByAuthor(String articleId);
}
