package fudan.se.lab2.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Main_Discussion") //主贴
public class Main_Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mainId; //主贴的id

    @Column
    private String content; //贴子的内容

    @Column
    private String username; //回贴人用户名

    @Column
    private String articleId; //对应的articleId

    @Column
    private String createTime; //回复时间

    private String discussStatus; //第一轮讨论还是第二轮讨论

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "main_discussion")
    private List<Sub_Discussion> sub_discussions;

    public Main_Discussion() {
    }

    public Main_Discussion(String articleId, String username, String discussStatus) {
        this.articleId = articleId;
        this.username = username;
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = temp.format(new Date()); //创建时间
        this.discussStatus = discussStatus;
    }

    public Main_Discussion(String articleId, String username, String content, String discussStatus) {
        this.articleId = articleId;
        this.username = username;
        this.content = content;
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = temp.format(new Date()); //创建时间
        this.discussStatus = discussStatus;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
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

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Sub_Discussion> getSub_discussions() {
        return sub_discussions;
    }

    public void setSub_discussions(List<Sub_Discussion> sub_discussions) {
        this.sub_discussions = sub_discussions;
    }

    public String getDiscussStatus() {
        return discussStatus;
    }

    public void setDiscussStatus(String discussStatus) {
        this.discussStatus = discussStatus;
    }

}
