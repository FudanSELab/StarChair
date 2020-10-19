package fudan.se.lab2.controller.request;

public class NewPostRequest {
    private String articleId;
    private String username;
    private String content;

    public NewPostRequest() {

    }

    public NewPostRequest(String articleId, String username, String content) {
        this.articleId = articleId;
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

}
