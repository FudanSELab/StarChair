package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity
@Table(name = "Invitation")
public class Invitation {
    @Id //主键
    @GeneratedValue(strategy = GenerationType.AUTO) //设置主键自增
    private Long id;

    @Column
    private String inviter;

    @Column
    private String invitee;

    @Column
    private String meetingId;

    @Column
    private String invitationStatus;

    public Invitation() {
    }

    public Invitation(String inviter, String invitee, String meetingId, String invitationStatus) {
        this.inviter = inviter;
        this.invitee = invitee;
        this.meetingId = meetingId;
        this.invitationStatus = invitationStatus;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getInvitee() {
        return invitee;
    }

    public void setInvitee(String invitee) {
        this.invitee = invitee;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }
}

