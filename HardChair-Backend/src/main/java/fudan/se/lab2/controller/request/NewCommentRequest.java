package fudan.se.lab2.controller.request;

public class NewCommentRequest {

    private String articleId;
    private String mainId;
    private String username;
    private String content;

    public NewCommentRequest() {

    }

    public NewCommentRequest(String articleId, String mainId, String username, String content) {
        this.articleId = articleId;
        this.mainId = mainId;
        this.username = username;
        this.content = content;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
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

}
