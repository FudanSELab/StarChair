package fudan.se.lab2.controller.request;

public class ScoreRequest {
    private String username;
    private String articleId;
    private String score;
    private String comment;
    private String confidence;

    public ScoreRequest() {
    }

    public ScoreRequest(String username, String articleId, String score, String comment, String confidence) {
        this.username = username;
        this.articleId = articleId;
        this.score = score;
        this.comment = comment;
        this.confidence = confidence;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}
