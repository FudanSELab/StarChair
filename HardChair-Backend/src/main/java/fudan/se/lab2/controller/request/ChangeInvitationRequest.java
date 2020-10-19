package fudan.se.lab2.controller.request;

public class ChangeInvitationRequest {
    private String id;
    private String invitationStatus;
    private String[] topics;

    public ChangeInvitationRequest() {
    }

    public ChangeInvitationRequest(String id, String invitationStatus, String[] topics) {
        this.id = id;
        this.invitationStatus = invitationStatus;
        this.topics = topics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

}
