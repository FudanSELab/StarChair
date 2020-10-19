package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //设置主键自增
    private Long id;//论文的唯一标识

    @Column
    private String articleName;

    @Column
    private String summary;

    @Column
    private String filePath;

    @Column
    private String contributor; //投稿人的用户名

    @Column
    private String meetingId; //投稿会议的ID

    @Column
    private String reviewStatus;

    @Column
    private String isAccepted; //是否被录用

    @Column
    private String rebuttal; //rebuttal理由

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "article")
    private Set<Topic> topics = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "article1")
    private Set<Author> authors = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "article2")
    private Set<Score> scores = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "article_pc", joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "pc_id")) //主表变 关联表变
    private Set<User> pcmembers = new HashSet<>();

    public Article() {
    }

    public Article(String articleName, String summary,
                   String filePath, String contributor, String meetingId, String reviewStatus) {
        this.articleName = articleName;
        this.summary = summary;
        this.filePath = filePath;
        this.contributor = contributor;
        this.meetingId = meetingId;
        this.reviewStatus = reviewStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Set<User> getPcmembers() {
        return pcmembers;
    }

    public void setPcmembers(Set<User> pcmembers) {
        this.pcmembers = pcmembers;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getRebuttal() {
        return rebuttal;
    }

    public void setRebuttal(String rebuttal) {
        this.rebuttal = rebuttal;
    }
}

