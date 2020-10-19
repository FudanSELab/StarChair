package fudan.se.lab2.controller.request;

public class ReplyCommentRequest {
    private String articleId;
    private String subId;
    private String username;
    private String content;

    public ReplyCommentRequest() {

    }

    public ReplyCommentRequest(String articleId, String subId, String username, String content) {
        this.articleId = articleId;
        this.subId = subId;
        this.username = username;
        this.content = content;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }
}
