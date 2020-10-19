package fudan.se.lab2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "Sub_Discussion") //回复
public class Sub_Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subId; //回复的id

    @Column
    private String articleId;

    @Column
    private String content; //回复的内容

    @Column
    private String username; //回复人用户名

    @Column
    private String fatherId; //如果是回复的回复，则有上级Id

    @Column
    private String createTime; //回复时间

    @Column
    private String reviewee; //被回复人username

    @Column
    private String discussStatus; //第一轮讨论还是第二轮讨论

    @ManyToOne
    @JoinColumn(name = "main_discussion", referencedColumnName = "mainId")
    @JsonIgnore
    private Main_Discussion main_discussion;

    public Sub_Discussion() {
    }

    public Sub_Discussion(String articleId, String content, String username, String discussStatus) {
        this.articleId = articleId;
        this.content = content;
        this.username = username;
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = temp.format(new Date()); //创建时间
        this.discussStatus = discussStatus;
    }

    public Sub_Discussion(String articleId, String content, String username, String fatherId, String reviewee, String discussStatus) {
        this.articleId = articleId;
        this.content = content;
        this.username = username;
        this.fatherId = fatherId;
        this.reviewee = reviewee;
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = temp.format(new Date()); //创建时间
        this.discussStatus = discussStatus;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReviewee() {
        return reviewee;
    }

    public void setReviewee(String reviewee) {
        this.reviewee = reviewee;
    }

    public Main_Discussion getMain_discussion() {
        return main_discussion;
    }

    public void setMain_discussion(Main_Discussion main_discussion) {
        this.main_discussion = main_discussion;
    }

    public String getDiscussStatus() {
        return discussStatus;
    }

    public void setDiscussStatus(String discussStatus) {
        this.discussStatus = discussStatus;
    }
}
