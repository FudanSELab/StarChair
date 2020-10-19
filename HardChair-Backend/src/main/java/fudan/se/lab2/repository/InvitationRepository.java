package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Invitation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Long> {
    @Query(value = "from Invitation where inviter = ?1")
    List<Invitation> findByInviter(String inviter);

    @Query(value = "from Invitation where invitee = ?1")
    List<Invitation> findByInvitee(String invitee);

    @Query(value = "from Invitation where inviter = ?1 and invitee = ?2 and meetingId = ?3")
    Optional<Invitation> findByInviterAndInviteeAndMeetingID(String inviter, String invitee, String meetingId);

    @Query(value = "from Invitation where invitee = ?1 and invitationStatus = ?2")
    List<Invitation> findByInviteeAndInvitationStatus(String invitee, String invitationStatus);
}

