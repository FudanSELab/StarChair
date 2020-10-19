package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends CrudRepository<Meeting, Long> {
    Meeting findByFullName(String fullName);

    @Query(value = "from Meeting where isVarified = ?1")
    List<Meeting> findByIsVarified(String isVarified);

    @Query(value = "from Meeting where applicant = ?1")
    List<Meeting> findByApplicant(String applicant);
}
