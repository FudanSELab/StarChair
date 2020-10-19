package fudan.se.lab2.controller.request;

public class SendInvitationRequest {
    private String meetingID;
    private String inviter;
    private String[] invitee;

    public SendInvitationRequest(){}

    public SendInvitationRequest(String meetingID, String inviter, String[] invitee) {
        this.meetingID = meetingID;
        this.inviter = inviter;
        this.invitee = invitee;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String[] getInvitee() {
        return invitee;
    }

    public void setInvitee(String[] invitee) {
        this.invitee = invitee;
    }
}
