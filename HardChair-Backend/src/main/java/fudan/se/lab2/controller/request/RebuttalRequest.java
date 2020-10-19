package fudan.se.lab2.controller.request;

public class RebuttalRequest {
    private String reason;
    private String username;
    private String meetingId;
    private String articleId;
    private String isRebuttaled;

    public RebuttalRequest() {

    }

    public RebuttalRequest(String reason, String username, String meetingId, String articleId, String isRebuttaled) {
        this.reason = reason;
        this.username = username;
        this.meetingId = meetingId;
        this.articleId = articleId;
        this.isRebuttaled = isRebuttaled;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getIsRebuttaled() {
        return isRebuttaled;
    }

    public void setIsRebuttaled(String isRebuttaled) {
        this.isRebuttaled = isRebuttaled;
    }

}
